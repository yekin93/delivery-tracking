package com.lrn.delivery_tracking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourierUpdateRequest(
		
		@NotBlank(message = "Phone must not be blank")
		@Size(max = 50, message = "Phone must not exceed 50 character")
		@Pattern(regexp = "^[0-9+\\-() ]+$", message = "Invalid phone format")
		String phone
		
		) {
	
}