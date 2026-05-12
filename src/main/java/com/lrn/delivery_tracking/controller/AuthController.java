package com.lrn.delivery_tracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrn.delivery_tracking.dto.request.LoginRequest;
import com.lrn.delivery_tracking.dto.request.RegisterRequest;
import com.lrn.delivery_tracking.dto.response.AuthResponse;
import com.lrn.delivery_tracking.dto.response.GlobalResponse;
import com.lrn.delivery_tracking.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<GlobalResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req){
		AuthResponse registered = authService.register(req);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new GlobalResponse<>(registered, HttpStatus.CREATED.value(), "Successfully registered"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<GlobalResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req){
		AuthResponse res = authService.login(req);
		return ResponseEntity.ok(new GlobalResponse<>(res, HttpStatus.OK.value(), "Successfully logedin"));
	}
	
}
