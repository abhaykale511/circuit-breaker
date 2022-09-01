package com.demo.circuitbreaker;

import java.util.Objects;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CBRegistry {

	private final CircuitBreakerConfig circuitBreakerConfig;

	private final CircuitBreakerRegistry circuitBreakerRegistry;
	
	public CBRegistry(CBConfig cbConfig) {
		this.circuitBreakerConfig=Objects.requireNonNull(cbConfig,"cbConfig must not be null").build();
		this.circuitBreakerRegistry=CircuitBreakerRegistry.of(this.circuitBreakerConfig);
	}

	public CircuitBreakerConfig getCircuitBreakerConfig() {
		return circuitBreakerConfig;
	}
	
	public CircuitBreakerRegistry getCircuitBreakerRegistry() {
		return circuitBreakerRegistry;
	}

}
