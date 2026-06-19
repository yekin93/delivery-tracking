package com.lrn.delivery_tracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrn.delivery_tracking.dto.response.ApplicationResponse;
import com.lrn.delivery_tracking.dto.response.GlobalResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;
import com.lrn.delivery_tracking.enums.ApplicationStatus;
import com.lrn.delivery_tracking.enums.ApplicationType;
import com.lrn.delivery_tracking.security.CustomUserDetails;
import com.lrn.delivery_tracking.service.ApplicationService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	private final ApplicationService applicationService;
	
	public AdminController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@GetMapping("/applications")
	public ResponseEntity<GlobalResponse<PageResponse<ApplicationResponse>>> getApplications(
									@RequestParam(required = false) ApplicationStatus status,
									@RequestParam(required = false) ApplicationType type,
									@RequestParam(defaultValue = "10")@Min(1) @Max(50) int size,
									@RequestParam(defaultValue = "0")@Min(0) int page) {
		
		PageResponse<ApplicationResponse> applications = applicationService.getApplications(status, type, size, page, "desc");
		
		return ResponseEntity.ok(new GlobalResponse<>(applications, HttpStatus.OK.value(), "Applications fetched successfully"));
	}
	
	@GetMapping("/applications/{id}")
	public ResponseEntity<GlobalResponse<ApplicationResponse>> getApplication(@PathVariable Long id){
		ApplicationResponse application = applicationService.getById(id);
		return ResponseEntity.ok(new GlobalResponse<>(application, HttpStatus.OK.value(), "Application fetched successfully"));
	}
	
	@PostMapping("/applications/{id}/approve")
	public ResponseEntity<GlobalResponse<ApplicationResponse>> approveApplication(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long id) {
		ApplicationResponse application = applicationService.approveApplication(user.getId(), id);
		return ResponseEntity.ok(new GlobalResponse<>(application, HttpStatus.OK.value(), "Application successfully approved"));
	}

}
