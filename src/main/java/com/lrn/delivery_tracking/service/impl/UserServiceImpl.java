package com.lrn.delivery_tracking.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.dto.response.UserResponse;
import com.lrn.delivery_tracking.entity.User;
import com.lrn.delivery_tracking.exception.NotFoundException;
import com.lrn.delivery_tracking.mapper.UserMapper;
import com.lrn.delivery_tracking.repository.UserRepository;
import com.lrn.delivery_tracking.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepo;
	
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse getUserById(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
		return UserMapper.toResponse(user);
	}

}
