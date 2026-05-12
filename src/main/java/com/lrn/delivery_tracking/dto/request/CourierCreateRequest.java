package com.lrn.delivery_tracking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourierCreateRequest(
		
		@NotBlank(message = "Name must not be blank")
		@Size(max = 100, message = "Name must not exceed 100 character")
		String name,
		
		@NotBlank(message = "Surname must not be blank")
		@Size(max = 100, message = "Surname must not exceed 100 character")
		String surname,
		
		@NotBlank(message = "Email must not be blank")
		@Email(message = "Please provide a valid email")
		@Size(max = 255, message = "Email must not exceed 255 character")
		@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Invalid email format")
		String email,
		
		@NotBlank(message = "Phone must not be blank")
		@Size(max = 50, message = "Phone must not exceed 50 character")
		@Pattern(regexp = "^[0-9+\\-() ]+$", message = "Invalid phone format")
		String phone
		
	) {}