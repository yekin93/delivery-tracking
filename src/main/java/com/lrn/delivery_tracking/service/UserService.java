package com.lrn.delivery_tracking.service;


import com.lrn.delivery_tracking.dto.response.UserResponse;

public interface UserService {

	UserResponse getUserById(Long id);
}
