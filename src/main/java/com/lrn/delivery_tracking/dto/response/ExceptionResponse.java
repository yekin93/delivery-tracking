package com.lrn.delivery_tracking.dto.response;

public record ExceptionResponse(
		
		Object message,
		int status,
		String error

		) {
	
}