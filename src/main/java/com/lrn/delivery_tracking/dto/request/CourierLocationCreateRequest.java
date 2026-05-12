package com.lrn.delivery_tracking.dto.request;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CourierLocationCreateRequest(
		@DecimalMin(value = "-90.0", message = "latitud must be >= -90")
		@DecimalMax(value = "90.0", message = "latitude <= 90")
		@NotNull(message = "Latitude must not be null")
		BigDecimal latitude,
		
		@DecimalMin(value = "-90.0", message = "latitud must be >= -90")
		@DecimalMax(value = "90.0", message = "latitude <= 90")
		@NotNull(message = "Longitude must not be null")
		BigDecimal longitude,
		
		BigDecimal speed,
		BigDecimal heading,
		BigDecimal accuracy,
		Instant recordedAt
		) {}