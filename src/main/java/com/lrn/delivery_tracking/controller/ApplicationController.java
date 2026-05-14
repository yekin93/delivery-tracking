package com.lrn.delivery_tracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrn.delivery_tracking.dto.request.ApplicationCreateRequest;
import com.lrn.delivery_tracking.dto.response.ApplicationResponse;
import com.lrn.delivery_tracking.dto.response.GlobalResponse;
import com.lrn.delivery_tracking.security.CustomUserDetails;
import com.lrn.delivery_tracking.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
	
	private final ApplicationService appService;
	
	public ApplicationController(ApplicationService appService) {
		this.appService = appService;
	}

	@PostMapping
	public ResponseEntity<GlobalResponse<ApplicationResponse>> createApplication(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody ApplicationCreateRequest req){
		ApplicationResponse res = appService.create(user.getId(), req);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new GlobalResponse<>(res, HttpStatus.CREATED.value(), "Your application successfully created"));
	}
	
	
}
