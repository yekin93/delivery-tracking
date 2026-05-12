package com.lrn.delivery_tracking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
		@NotBlank(message = "Email must not be blank")
		@Size(max = 255, message = "Password must not exceed 255 character")
		@Email(message = "please provide a valid email")
		String email,
		
		@NotBlank(message = "Password must not be blank")
		String password
		
		) {

}
