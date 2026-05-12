package com.lrn.delivery_tracking.service;

import com.lrn.delivery_tracking.dto.request.LoginRequest;
import com.lrn.delivery_tracking.dto.request.RegisterRequest;
import com.lrn.delivery_tracking.dto.response.AuthResponse;

public interface AuthService {

	AuthResponse register(RegisterRequest req);
	AuthResponse login(LoginRequest req);
}
