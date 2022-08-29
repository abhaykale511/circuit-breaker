package com.demo.aspect;

import static com.demo.util.SupplierUtil.rethrowSupplier;

import java.net.SocketTimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.exception.DBTimeOutException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;

@Aspect
@Component
public class DBAspect {
	@Autowired
	private CircuitBreaker circuitBreaker;

	private static final Logger LOG = LoggerFactory.getLogger(DBAspect.class);

	@Around("execution(* com.demo.dao.*.*(..))")
	public Object circuitWrap(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = null;
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String className = methodSignature.getDeclaringType().getName();
		String methodName = methodSignature.getName();
		try {

			returnValue = execute(rethrowSupplier(joinPoint::proceed), this::fallback);
		} catch (Exception e) {
			LOG.error("", e);
			throw new Throwable(e);
		}
		LOG.info("Around after class - {}, method - {}, returns - {}", className, methodName, returnValue);

		return returnValue;
	}

	private <T> T execute(Supplier<T> supplier, Function<Throwable, T> fallback) {
		return Decorators.ofSupplier(supplier).withCircuitBreaker(circuitBreaker).withFallback(fallback).get();
	}

	private Object fallback(Throwable ex) {
		throw new RuntimeException(ex);
	}

}