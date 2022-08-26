package com.demo.aspect;

import java.net.SocketTimeoutException;
import java.util.function.Supplier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.exception.DBTimeOutException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;

@Aspect
@Component
public class DBAspect {
	@Autowired
	private CircuitBreaker circuitBreaker;
	

	@Around("execution(* com.demo.dao.*.*(..))")
	public Object circuitWrap(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		System.out.println("Before method call:" + className + "." + methodName);

		
		Supplier<Object> objectSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
			try {
				return joinPoint.proceed();
			} catch (SocketTimeoutException e) {
				throw new DBTimeOutException(e);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		});
		Object obj = Try.ofSupplier(objectSupplier).get();

		System.out.println("After method call:" + className + "." + methodName);
		return obj;
	}
}
