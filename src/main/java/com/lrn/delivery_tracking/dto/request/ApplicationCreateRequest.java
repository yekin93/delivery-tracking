package com.lrn.delivery_tracking.dto.request;

import com.lrn.delivery_tracking.enums.ApplicationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ApplicationCreateRequest(
		
		@NotNull(message = "Type must not be empty")
		ApplicationType type,
		
		@Size(max = 100, message = "Applicant name must not exceed 100 character")
		String applicantName,
		
		@Size(max = 100, message = "Applicant name must be exceed 100 character")
		String applicantSurname,
		
		@Size(max = 100, message = "Business name must not exceed 100 character")
		String businessName,
		
		@NotBlank(message = "Email must not be empty")
		@Email(message = "Please provide a valid email")
		@Size(max = 150, message = "Email must not exceed 150 charcter")
		@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Invalid email format")
		String email,
		
		@NotBlank(message = "Phone must not be empty")
		@Size(max = 30)
		@Pattern(regexp = "^[0-9+\\-() ]+$", message = "Invalid phone format")
		String phone,
		
		String message
		) {

}
