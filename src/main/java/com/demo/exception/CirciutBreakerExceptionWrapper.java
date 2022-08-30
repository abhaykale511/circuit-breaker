package com.demo.exception;

public class CirciutBreakerExceptionWrapper extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    public CirciutBreakerExceptionWrapper() {
        super();
    }

    public CirciutBreakerExceptionWrapper(Throwable cause) {
        super(cause);
    }
    public CirciutBreakerExceptionWrapper(String message) {
        super(message);
    }

}
