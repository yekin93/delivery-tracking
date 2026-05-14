package com.lrn.delivery_tracking.dto.response;

import java.time.Instant;

public record ApplicationResponse(
		
		Long id,
		String applicantName,
		String businessName,
		String type,
		String status,
		String email,
		String phone,
		String message,
		Instant createdAt,
		Instant reviewedAt
		
		) {

}
