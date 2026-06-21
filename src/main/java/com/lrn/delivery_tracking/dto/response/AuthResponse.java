package com.lrn.delivery_tracking.dto.response;

import java.time.Instant;


public record AuthResponse(
		Long id,
		String firstName,
		String lastName,
		String email,
		Instant createdAt,
		String token
		) {

}
