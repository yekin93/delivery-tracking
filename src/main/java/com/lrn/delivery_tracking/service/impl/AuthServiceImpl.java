package com.lrn.delivery_tracking.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.dto.request.LoginRequest;
import com.lrn.delivery_tracking.dto.request.RegisterRequest;
import com.lrn.delivery_tracking.dto.response.AuthResponse;
import com.lrn.delivery_tracking.entity.User;
import com.lrn.delivery_tracking.exception.AlreadyExistsException;
import com.lrn.delivery_tracking.exception.InvalidCredentialsException;
import com.lrn.delivery_tracking.exception.UserDisabledException;
import com.lrn.delivery_tracking.mapper.AuthMapper;
import com.lrn.delivery_tracking.repository.UserRepository;
import com.lrn.delivery_tracking.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(UserRepository userRepo,
							PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@Override
	@Transactional
	public AuthResponse register(RegisterRequest req) {
		if(userRepo.existsByEmail(req.email())) {
			throw new AlreadyExistsException(req.email() + " already in use!");
		}
		
		User user = AuthMapper.toEntity(req);
		user.setPasswordHash(passwordEncoder.encode(req.password()));
		
		User registered = userRepo.save(user);
		
		return AuthMapper.toResponse(registered);
	}


	@Override
	@Transactional(readOnly = true)
	public AuthResponse login(LoginRequest req) {
		User user = userRepo.findByEmail(req.email()).orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
		
		if(!user.isEnabled()) {
			throw new RuntimeException("User is disabled");
		}
		
		if(!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
			throw new UserDisabledException("Invalid Credentials");
		}
		
		return AuthMapper.toResponse(user);
	}

}
