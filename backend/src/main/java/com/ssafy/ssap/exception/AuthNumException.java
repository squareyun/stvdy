package com.ssafy.ssap.exception;

public class AuthNumException extends RuntimeException {
	public AuthNumException() {
		super();
	}
	public AuthNumException(String message, Throwable cause) {
		super(message, cause);
	}
	public AuthNumException(String message) {
		super(message);
	}
	public AuthNumException(Throwable cause) {
		super(cause);
	}
}
