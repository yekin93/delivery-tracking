package com.lrn.delivery_tracking.mapper;

import com.lrn.delivery_tracking.dto.request.RegisterRequest;
import com.lrn.delivery_tracking.dto.response.AuthResponse;
import com.lrn.delivery_tracking.entity.User;

public class AuthMapper {
	
	private AuthMapper() {}
	
	public static AuthResponse toResponse(User user, String token) {
		return new AuthResponse(user.getId(),
								user.getFirstName(),
								user.getLastName(),
								user.getEmail(),
								user.getRole(),
								user.getCreatedAt(),
								token);
	}
	
	public static User toEntity(RegisterRequest req) {
		User user = new User();
		user.setFirstName(req.firstName());
		user.setLastName(req.lastName());
		user.setEmail(req.email());
		user.setPasswordHash(req.password());
		return user;
	}

}
