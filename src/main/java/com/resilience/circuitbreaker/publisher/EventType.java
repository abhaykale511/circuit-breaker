package com.resilience.circuitbreaker.publisher;

public enum EventType {

	ON_SUCCESS, ON_ERROR, ON_STATE_TRANSITION, ON_RESET, ON_IGNORED_ERROR, ON_CALL_NOT_PERMITTED,
	ON_FAILURE_RATE_EXCEEDED, ON_SLOW_CALL_RATE_EXCEEDED;


}
