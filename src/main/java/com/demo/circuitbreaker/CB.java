package com.demo.circuitbreaker;

import java.util.Objects;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CB {
	
	private final CircuitBreaker circuitBreaker;

	private final CircuitBreakerRegistry circuitBreakerRegistry;

	private final CircuitBreakerConfig circuitBreakerConfig;

	public CB(CBRegistry cbRegistry, String cbName) {
		this.circuitBreakerRegistry =Objects.requireNonNull(cbRegistry,"cbRegistry must not be null").getCircuitBreakerRegistry();
		this.circuitBreakerConfig = cbRegistry.getCircuitBreakerConfig();
		circuitBreaker = circuitBreakerRegistry.circuitBreaker(Objects.requireNonNull(cbName, "cbName must not be null"));
	}

	public CircuitBreaker getCircuitBreaker() {
		return circuitBreaker;
	}

	public CircuitBreakerRegistry CircuitBreakerRegistry() {
		return circuitBreakerRegistry;
	}

	public CircuitBreakerConfig getCircuitBreakerConfig() {
		return circuitBreakerConfig;
	}
}
