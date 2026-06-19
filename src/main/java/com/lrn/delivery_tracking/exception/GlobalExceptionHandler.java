package com.lrn.delivery_tracking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lrn.delivery_tracking.dto.response.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleAlreadyExists(AlreadyExistsException ex){
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(new ExceptionResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), "Already Exists"));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException ex){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Not Found"));
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		
		for(FieldError err : ex.getBindingResult().getFieldErrors()) {
			errors.put(err.getField(), err.getDefaultMessage());
		}
		
		return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ExceptionResponse(errors, HttpStatus.BAD_REQUEST.value(), "Bad Request"));
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidCredentials(InvalidCredentialsException ex){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Bad Request"));
	}
	
	@ExceptionHandler(UserDisabledException.class)
	public ResponseEntity<ExceptionResponse> handleUserDisabled(UserDisabledException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Bad Request"));
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequestException ex){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Bad Request"));
	}
	
}
