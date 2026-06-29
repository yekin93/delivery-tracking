package com.lrn.delivery_tracking.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.dto.request.LoginRequest;
import com.lrn.delivery_tracking.dto.request.RegisterRequest;
import com.lrn.delivery_tracking.dto.response.AuthResponse;
import com.lrn.delivery_tracking.entity.Role;
import com.lrn.delivery_tracking.entity.User;
import com.lrn.delivery_tracking.enums.RoleType;
import com.lrn.delivery_tracking.exception.AlreadyExistsException;
import com.lrn.delivery_tracking.exception.InvalidCredentialsException;
import com.lrn.delivery_tracking.exception.UserDisabledException;
import com.lrn.delivery_tracking.mapper.AuthMapper;
import com.lrn.delivery_tracking.repository.RoleRepository;
import com.lrn.delivery_tracking.repository.UserRepository;
import com.lrn.delivery_tracking.security.JwtService;
import com.lrn.delivery_tracking.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final RoleRepository roleRepo;
	
	public AuthServiceImpl(UserRepository userRepo,
							PasswordEncoder passwordEncoder,
							JwtService jwtService,
							RoleRepository roleRepo) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.roleRepo = roleRepo;
	}
	
	
	@Override
	@Transactional
	public AuthResponse register(RegisterRequest req) {
		if(userRepo.existsByEmail(req.email())) {
			throw new AlreadyExistsException(req.email() + " already in use!");
		}
		
		User user = AuthMapper.toEntity(req);
		user.setPasswordHash(passwordEncoder.encode(req.password()));
		Role role = roleRepo.findByName(RoleType.CUSTOMER.name()).orElse(null);
		if(role != null) user.getRoles().add(role);
		User registered = userRepo.save(user);
		String token = jwtService.generateAccessToken(registered);
		return AuthMapper.toResponse(registered, token);
	}


	@Override
	@Transactional(readOnly = true)
	public AuthResponse login(LoginRequest req) {
		User user = userRepo.findByEmailWithRoles(req.email()).orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
		
		if(!user.isEnabled()) {
			throw new UserDisabledException("User is disabled");
		}
		
		if(!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
			throw new InvalidCredentialsException("Invalid Credentials");
		}
		
		String token = jwtService.generateAccessToken(user);
		
		return AuthMapper.toResponse(user, token);
	}

}
