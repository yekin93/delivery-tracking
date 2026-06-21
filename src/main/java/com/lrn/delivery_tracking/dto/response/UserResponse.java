package com.lrn.delivery_tracking.dto.response;

import java.util.Set;

import com.lrn.delivery_tracking.entity.Role;

public record UserResponse(
			Long id,
			String firstName,
			String lastName,
			String email,
			Set<Role> roles
		) {

}
