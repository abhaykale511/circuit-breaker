package com.demo.event.consumer;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;

public class DBCircutStateEventConumer implements EventConsumer<CircuitBreakerOnStateTransitionEvent> {

	@Override
	public void consumeEvent(CircuitBreakerOnStateTransitionEvent event) {
		System.out.println(event.getStateTransition());
		switch (event.getStateTransition()) {
		case CLOSED_TO_OPEN:
		case HALF_OPEN_TO_OPEN:
			System.out.println("Set DB UnHealthy");
			break;
		case OPEN_TO_CLOSED:
		case HALF_OPEN_TO_CLOSED:
			System.out.println("Set DB Healthy");
			break;
		default:
			break;
		}

	}

}
