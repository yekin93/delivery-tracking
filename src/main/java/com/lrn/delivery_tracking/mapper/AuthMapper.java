package com.lrn.delivery_tracking.mapper;

import java.util.ArrayList;
import java.util.List;

import com.lrn.delivery_tracking.dto.request.RegisterRequest;
import com.lrn.delivery_tracking.dto.response.AuthResponse;
import com.lrn.delivery_tracking.entity.Role;
import com.lrn.delivery_tracking.entity.User;

public class AuthMapper {
	
	private AuthMapper() {}
	
	public static AuthResponse toResponse(User user, String token) {
		List<String> roles = new ArrayList<>();
		if(user.getRoles() != null && user.getRoles().size() > 0) {
			roles = user.getRoles().stream().map(Role::getName).toList();
		}
		return new AuthResponse(user.getId(),
								user.getFirstName(),
								user.getLastName(),
								user.getEmail(),
								user.getCreatedAt(),
								token,
								roles);
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
