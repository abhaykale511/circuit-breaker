package com.resilience.circuitbreaker.publisher;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resilience.circuitbreaker.CB;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnIgnoredErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnResetEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSlowCallRateExceededEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;
import io.github.resilience4j.core.EventConsumer;

final public class CBEventSubscription {

	private static final Logger LOG = LoggerFactory.getLogger(CBEventSubscription.class);
	
	final private CB cb;
	
	public CBEventSubscription(CB cb, Map<EventType,EventConsumer<? extends CircuitBreakerEvent>> eventConsumerMap) {
		this.cb = Objects.requireNonNull(cb);		
		Objects.requireNonNull(eventConsumerMap)
		.forEach(this::registerConusmer);

	}

	@SuppressWarnings("unchecked")
	private void registerConusmer(EventType event,EventConsumer<? extends CircuitBreakerEvent> consumer) {

		LOG.debug("Event: {} Event Consumer: {}", event,consumer);
		
		switch (event) {
		
		case ON_SUCCESS:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onSuccess((EventConsumer<CircuitBreakerOnSuccessEvent>) consumer);
			break;
			
		case ON_ERROR:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onError((EventConsumer<CircuitBreakerOnErrorEvent>) consumer);
			break;	
			
		case ON_STATE_TRANSITION:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onStateTransition((EventConsumer<CircuitBreakerOnStateTransitionEvent>) consumer);
			break;
			
		case ON_RESET:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onReset((EventConsumer<CircuitBreakerOnResetEvent>) consumer);
			break;
			
		case ON_IGNORED_ERROR:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onIgnoredError((EventConsumer<CircuitBreakerOnIgnoredErrorEvent>) consumer);
			break;	
			
		case ON_CALL_NOT_PERMITTED:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onCallNotPermitted((EventConsumer<CircuitBreakerOnCallNotPermittedEvent>) consumer);
			break;	
			
		case ON_FAILURE_RATE_EXCEEDED:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onFailureRateExceeded((EventConsumer<CircuitBreakerOnFailureRateExceededEvent>) consumer);
			break;	
			
		case ON_SLOW_CALL_RATE_EXCEEDED:
			cb.getCircuitBreaker()
			.getEventPublisher()
			.onSlowCallRateExceeded((EventConsumer<CircuitBreakerOnSlowCallRateExceededEvent>) consumer);
			break;	
			
		default:
			break;
		}
		
	}

}
