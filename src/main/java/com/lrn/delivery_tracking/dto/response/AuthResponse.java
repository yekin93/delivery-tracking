package com.lrn.delivery_tracking.dto.response;

import java.time.Instant;
import java.util.List;


public record AuthResponse(
		Long id,
		String firstName,
		String lastName,
		String email,
		Instant createdAt,
		String token,
		List<String> roles
		) {

}
