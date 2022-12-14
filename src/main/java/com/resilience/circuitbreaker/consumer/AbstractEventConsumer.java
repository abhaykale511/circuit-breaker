package com.resilience.circuitbreaker.consumer;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.core.EventConsumer;

public abstract class AbstractEventConsumer<T extends CircuitBreakerEvent> implements EventConsumer<T>{
	
	@Override
	final public void consumeEvent(T event) {
		consume(event);
	}

	public abstract void consume(T event);

}
