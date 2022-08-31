package com.demo.cb.publisher;

import com.demo.cb.CB;

import io.github.resilience4j.circuitbreaker.CircuitBreaker.EventPublisher;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;

public class CBEventPublisher {
	
	private final EventPublisher eventPublisher;
		
	public CBEventPublisher(CB cb,EventConsumer<CircuitBreakerOnStateTransitionEvent> eventConsumer) {
		eventPublisher=cb.getCircuitBreaker().getEventPublisher().onStateTransition(eventConsumer);
	}

	public EventPublisher getEventPublisher() {
		return eventPublisher;
	}
	
}
