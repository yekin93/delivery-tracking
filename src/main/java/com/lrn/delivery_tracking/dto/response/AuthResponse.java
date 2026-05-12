package com.lrn.delivery_tracking.dto.response;

import java.time.Instant;

import com.lrn.delivery_tracking.enums.Role;

public record AuthResponse(
		Long id,
		String firstName,
		String lastName,
		String email,
		Role role,
		Instant createdAt
		) {

}
