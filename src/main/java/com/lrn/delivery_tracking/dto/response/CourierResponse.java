package com.lrn.delivery_tracking.dto.response;

import java.time.Instant;

import com.lrn.delivery_tracking.enums.CourierStatus;

public record CourierResponse(
		
		Long id,
		String name,
		String surname,
		CourierStatus status,
		String email,
		String phone,
		Instant createdAt
		
		) {}