package com.demo.event.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;

public class DBCircutStateEventConumer implements EventConsumer<CircuitBreakerOnStateTransitionEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(DBCircutStateEventConumer.class);

	@Override
	public void consumeEvent(CircuitBreakerOnStateTransitionEvent event) {
		LOG.info("{} {}", event.getCircuitBreakerName(), event.getStateTransition());

		switch (event.getStateTransition()) {
		case CLOSED_TO_OPEN:
		case HALF_OPEN_TO_OPEN:
			LOG.debug("Set DB UnHealthy");
			break;
		
		case OPEN_TO_CLOSED:
		case HALF_OPEN_TO_CLOSED:
			LOG.debug("Set DB Healthy");
			break;
		default:
			break;
		}

	}

}
