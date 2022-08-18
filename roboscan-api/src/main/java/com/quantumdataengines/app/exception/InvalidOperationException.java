package com.quantumdataengines.app.exception;

@SuppressWarnings("serial")
public class InvalidOperationException extends RuntimeException {
	
	public InvalidOperationException() {
		this("Invalid Operation");
	}
	
	public InvalidOperationException(String message) {
		super(message);
	}
}
