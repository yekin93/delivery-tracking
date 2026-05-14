package com.lrn.delivery_tracking.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.dto.request.ApplicationCreateRequest;
import com.lrn.delivery_tracking.dto.response.ApplicationResponse;
import com.lrn.delivery_tracking.entity.Application;
import com.lrn.delivery_tracking.entity.User;
import com.lrn.delivery_tracking.exception.AlreadyExistsException;
import com.lrn.delivery_tracking.exception.NotFoundException;
import com.lrn.delivery_tracking.mapper.ApplicationMapper;
import com.lrn.delivery_tracking.repository.ApplicationRepository;
import com.lrn.delivery_tracking.repository.CourierRepository;
import com.lrn.delivery_tracking.repository.UserRepository;
import com.lrn.delivery_tracking.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	private final ApplicationRepository appRepo;
	private final UserRepository userRepo;
	private final CourierRepository courierRepo;
	
	public ApplicationServiceImpl(ApplicationRepository appRepo,
								UserRepository userRepo,
								CourierRepository courierRepo) {
		this.appRepo = appRepo;
		this.userRepo = userRepo;
		this.courierRepo = courierRepo;
	}

	@Override
	@Transactional
	public ApplicationResponse create(Long userId, ApplicationCreateRequest req) {
		if(courierRepo.existsByUserId(userId)) {
			throw new AlreadyExistsException("Already exists courier for user id : " + userId);
		}
		
		User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id:" + userId));
		Application application = ApplicationMapper.toEntity(req);
		application.setUser(user);
		Application createdApplication = appRepo.save(application);
		return ApplicationMapper.toResponse(createdApplication);
	}

}
