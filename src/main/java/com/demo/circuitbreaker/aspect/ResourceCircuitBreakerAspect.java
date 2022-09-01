package com.demo.circuitbreaker.aspect;

import static com.demo.circuitbreaker.util.SupplierUtil.rethrowSupplier;

import java.util.function.Function;
import java.util.function.Supplier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.demo.circuitbreaker.CB;
import com.demo.circuitbreaker.exception.CircuitBreakerExceptionWrapper;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;

public class ResourceCircuitBreakerAspect implements ApplicationContextAware{

	private final CircuitBreaker circuitBreaker;
	
	private final String flagName;

	private ApplicationContext context;

	private static final Logger LOG = LoggerFactory.getLogger(ResourceCircuitBreakerAspect.class);
	
	public ResourceCircuitBreakerAspect(CB cb,String flagName) {
		circuitBreaker=cb.getCircuitBreaker();
		this.flagName=flagName;
	}
	

	final public Object circuitWrap(ProceedingJoinPoint joinPoint) throws Throwable {

		boolean enableCircuitBreaker = Boolean
				.parseBoolean(context.getEnvironment().getProperty(flagName));

		Object returnValue = enableCircuitBreaker ? proceedWithCircuit(joinPoint) : joinPoint.proceed();
        
		LOG.info("Around after class - {}, method - {}, returns - {}",
				joinPoint.getSignature().getDeclaringType().getName(), joinPoint.getSignature().getName(), returnValue);

		return returnValue;
	}

	private Object proceedWithCircuit(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = null;
		try {
			returnValue = execute(rethrowSupplier(joinPoint::proceed), this::fallback);
		} catch (CircuitBreakerExceptionWrapper e) {
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
		throw new CircuitBreakerExceptionWrapper(ex);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}