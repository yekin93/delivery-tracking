package com.lrn.delivery_tracking.mapper;

import com.lrn.delivery_tracking.dto.response.UserResponse;
import com.lrn.delivery_tracking.entity.User;

public class UserMapper {
	
	private UserMapper() {}
	
	public static UserResponse toResponse(User user) {
		return new UserResponse(user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getEmail(),
					user.getRoles());
	}

}
