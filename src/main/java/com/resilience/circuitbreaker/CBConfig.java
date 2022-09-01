package com.resilience.circuitbreaker;

import java.time.Duration;
import java.util.List;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.core.IntervalFunction;

public class CBConfig {

	private List<String> recordExceptions;

	private float failureRateThreshold = 50f;

	private long waitIntervalFunctionInOpenState = 1;

	private int permittedNumberOfCallsInHalfOpenState = 10;

	private int slidingWindowSize = 100;

	private int minimumNumberOfCalls = 100;

	public final CircuitBreakerConfig build() {
		return CircuitBreakerConfig.custom().failureRateThreshold(failureRateThreshold)
				.waitIntervalFunctionInOpenState(IntervalFunction.of(Duration.ofMillis(waitIntervalFunctionInOpenState)))
				.permittedNumberOfCallsInHalfOpenState(permittedNumberOfCallsInHalfOpenState)
				.slidingWindow(slidingWindowSize, minimumNumberOfCalls,CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
				.recordException(t->instanceOf(t,recordExceptions))
				.build();
	}

	public void setRecordExceptions(List<String> recordExceptions) {
		this.recordExceptions = recordExceptions;
	}

	public void setFailureRateThreshold(float failureRateThreshold) {
		this.failureRateThreshold = failureRateThreshold;
	}

	public void setWaitIntervalFunctionInOpenState(long waitIntervalFunctionInOpenState) {
		this.waitIntervalFunctionInOpenState = waitIntervalFunctionInOpenState;
	}

	public void setPermittedNumberOfCallsInHalfOpenState(int permittedNumberOfCallsInHalfOpenState) {
		this.permittedNumberOfCallsInHalfOpenState = permittedNumberOfCallsInHalfOpenState;
	}

	public void setSlidingWindowSize(int slidingWindowSize) {
		this.slidingWindowSize = slidingWindowSize;
	}

	public void setMinimumNumberOfCalls(int minimumNumberOfCalls) {
		this.minimumNumberOfCalls = minimumNumberOfCalls;
	}

	@Override
	public String toString() {
		return "CBConfig [recordExceptions=" + recordExceptions + ", failureRateThreshold=" + failureRateThreshold
				+ ", waitIntervalFunctionInOpenState=" + waitIntervalFunctionInOpenState
				+ ", permittedNumberOfCallsInHalfOpenState=" + permittedNumberOfCallsInHalfOpenState
				+ ", slidingWindowSize=" + slidingWindowSize + ", minimumNumberOfCalls=" + minimumNumberOfCalls + "]";
	}

	private boolean instanceOf(Throwable t, List<String> c) {
		return c.contains(t.getClass().getCanonicalName());
	}

}
