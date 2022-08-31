package com.demo.cb.exception;

public class CircuitBreakerExceptionWrapper extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CircuitBreakerExceptionWrapper() {
		super();
	}

	public CircuitBreakerExceptionWrapper(Throwable cause) {
		super(cause);
	}

	public CircuitBreakerExceptionWrapper(String message) {
		super(message);
	}

	public CircuitBreakerExceptionWrapper(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public CircuitBreakerExceptionWrapper(String message, Throwable cause) {
		super(message, cause);

	}

}
