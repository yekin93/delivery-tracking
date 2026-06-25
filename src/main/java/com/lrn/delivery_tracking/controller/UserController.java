package com.lrn.delivery_tracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrn.delivery_tracking.dto.response.GlobalResponse;
import com.lrn.delivery_tracking.dto.response.UserResponse;
import com.lrn.delivery_tracking.security.CustomUserDetails;
import com.lrn.delivery_tracking.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public ResponseEntity<GlobalResponse<UserResponse>> me(@AuthenticationPrincipal CustomUserDetails auth) {
		UserResponse user = userService.getUserById(auth.getId());
		return ResponseEntity.ok(new GlobalResponse<>(user, HttpStatus.OK.value(), "User fetched successfully"));
	}
	
}
