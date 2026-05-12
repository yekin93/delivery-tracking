package com.lrn.delivery_tracking.dto.response;

public record GlobalResponse<T>(
		T data,
		int status,
		String message
		){
	
}