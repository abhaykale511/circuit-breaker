package com.demo.exception;

public class DBTimeOutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    public DBTimeOutException() {
        super();
    }

    public DBTimeOutException(Throwable cause) {
        super(cause);
    }
    public DBTimeOutException(String message) {
        super(message);
    }

}
