package com.lrn.delivery_tracking.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record CourierLocationResponse(
		
		Long id,
		BigDecimal latitude,
		BigDecimal longitude,
		Instant recordedAt
		
		) {}