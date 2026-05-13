package com.lrn.delivery_tracking.exception;

public class UserDisabledException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserDisabledException(String message) {
		super(message);
	}
}
