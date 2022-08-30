package com.demo.aspect;

import static com.demo.util.SupplierUtil.rethrowSupplier;

import java.util.function.Function;
import java.util.function.Supplier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.demo.exception.CirciutBreakerExceptionWrapper;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;

public class ResourceCircuitBreakerAspect implements ApplicationContextAware {

	private CircuitBreaker circuitBreaker;

	private String circuitBreakerFlagProp;

	private ApplicationContext context;

	private static final Logger LOG = LoggerFactory.getLogger(ResourceCircuitBreakerAspect.class);

	final public Object circuitWrap(ProceedingJoinPoint joinPoint) throws Throwable {

		boolean enableCircuitBreaker = Boolean
				.parseBoolean(context.getEnvironment().getProperty(circuitBreakerFlagProp));

		Object returnValue = enableCircuitBreaker ? proceedWithCircuit(joinPoint) : joinPoint.proceed();

		LOG.info("Around after class - {}, method - {}, returns - {}",
				joinPoint.getSignature().getDeclaringType().getName(), joinPoint.getSignature().getName(), returnValue);

		return returnValue;
	}

	private Object proceedWithCircuit(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = null;
		try {
			returnValue = execute(rethrowSupplier(joinPoint::proceed), this::fallback);
		} catch (CirciutBreakerExceptionWrapper e) {
			Throwable ex = e.getCause();
			LOG.error("Exception in {} aspect", circuitBreaker.getName(), ex);
			if (!(ex instanceof CallNotPermittedException))
				throw ex;
		}
		return returnValue;
	}

	private <T> T execute(Supplier<T> supplier, Function<Throwable, T> fallback) {
		return Decorators.ofSupplier(supplier)
				.withCircuitBreaker(circuitBreaker)
				.withFallback(fallback)
				.get();
	}

	private Object fallback(Throwable ex) {
		throw new CirciutBreakerExceptionWrapper(ex);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public void setCircuitBreakerFlagProp(String circuitBreakerFlagProp) {
		this.circuitBreakerFlagProp = circuitBreakerFlagProp;
	}

	public void setCircuitBreaker(CircuitBreaker circuitBreaker) {
		this.circuitBreaker = circuitBreaker;
	}
}