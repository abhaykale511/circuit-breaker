package com.demo.cb.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;

public class StateTranEventConsumer extends AbstractCBEventConsumer<CircuitBreakerOnStateTransitionEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(StateTranEventConsumer.class);

	@Override
	public void consume(CircuitBreakerOnStateTransitionEvent event) {

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
