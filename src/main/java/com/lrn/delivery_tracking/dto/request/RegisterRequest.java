package com.lrn.delivery_tracking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		@NotBlank(message = "First name must not be empty")
		@Size(max = 100, message = "Firs name must not exceed 100 character")
		String firstName,
		
		@NotBlank(message = "Last name must not be empty")
		@Size(max = 100, message = "Last name must not exceed 100 character")
		String lastName,
		
		@NotBlank(message = "Email must not be empty")
		@Email(message = "Please provide a valid email")
		@Size(max = 255, message = "Email must not exceed 255 character")
		@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Invalid email format")
		String email,
		
		@NotBlank(message = "Password must not be emptry")
		String password
		) {

}
