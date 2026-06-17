package com.lrn.delivery_tracking.mapper;

import com.lrn.delivery_tracking.dto.request.ApplicationCreateRequest;
import com.lrn.delivery_tracking.dto.response.ApplicationResponse;
import com.lrn.delivery_tracking.entity.Application;

public class ApplicationMapper {

	
	public static ApplicationResponse toResponse(Application application) {
		return new ApplicationResponse(application.getId(),
				application.getApplicantName(),
				application.getApplicantSurname(),
				application.getBusinessName(),
				application.getType().name(),
				application.getStatus().name(),
				application.getEmail(),
				application.getPhone(),
				application.getMessage(),
				application.getCreatedAt(),
				application.getReviewedAt());
	}
	
	public static Application toEntity(ApplicationCreateRequest req) {
		Application app = new Application();
		app.setApplicantName(req.applicantName());
		app.setApplicantSurname(req.applicantSurname());
		app.setBusinessName(req.businessName());
		app.setType(req.type());
		app.setEmail(req.email());
		app.setPhone(req.phone());
		app.setMessage(req.message());
		
		return app;
	}
	
}
