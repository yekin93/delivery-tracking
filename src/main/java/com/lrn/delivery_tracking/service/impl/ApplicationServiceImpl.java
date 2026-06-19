package com.lrn.delivery_tracking.service.impl;


import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.dto.request.ApplicationCreateRequest;
import com.lrn.delivery_tracking.dto.response.ApplicationResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;
import com.lrn.delivery_tracking.entity.Application;
import com.lrn.delivery_tracking.entity.Courier;
import com.lrn.delivery_tracking.entity.User;
import com.lrn.delivery_tracking.enums.ApplicationStatus;
import com.lrn.delivery_tracking.enums.ApplicationType;
import com.lrn.delivery_tracking.exception.AlreadyExistsException;
import com.lrn.delivery_tracking.exception.BadRequestException;
import com.lrn.delivery_tracking.exception.NotFoundException;
import com.lrn.delivery_tracking.mapper.ApplicationMapper;
import com.lrn.delivery_tracking.mapper.CourierMapper;
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

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ApplicationResponse> getApplications(ApplicationStatus status, ApplicationType type, int size, int page, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createdAt"));
		Page<Application> applicationsPage = appRepo.findByFilters(type, status, pageable);
		
		List<ApplicationResponse> appList = applicationsPage
								.getContent()
								.stream()
								.map(ApplicationMapper::toResponse)
								.toList();
		return new PageResponse<ApplicationResponse>(appList, applicationsPage.getNumber(), applicationsPage.getSize(), applicationsPage.getTotalElements(), applicationsPage.getTotalPages());
	}

	@Override
	@Transactional(readOnly = true)
	public ApplicationResponse getById(Long id) {
		Application application = appRepo.findById(id).orElseThrow(() -> new NotFoundException("Application not found with id " + id));
		return ApplicationMapper.toResponse(application);
	}

	@Override
	@Transactional
	public ApplicationResponse approveApplication(Long approverId, Long applicationId) {
		User approver = userRepo.findById(approverId).orElseThrow(() -> new NotFoundException("Not found approver with id: " + approverId));
		Application application = appRepo.findById(applicationId).orElseThrow(() -> new NotFoundException("Application not found with id: " + applicationId));
		
		if(application.getStatus().equals(ApplicationStatus.APPROVED)) {
			throw new BadRequestException("Application has already been approved.");
		}
		
		if(application.getStatus().equals(ApplicationStatus.REJECTED)) {
			throw new BadRequestException("Application has already been rejected.");
		}
		
		Courier courier = CourierMapper.applicationToCourier(application);
		courier.setUser(application.getUser());
		courierRepo.save(courier);
		
		application.setStatus(ApplicationStatus.APPROVED);
		application.setReviewed(approver);
		application.setReviewedAt(Instant.now());
		
		
		Application approved = appRepo.save(application);

		return ApplicationMapper.toResponse(approved);
	}

	@Override
	@Transactional
	public ApplicationResponse rejectApplication(Long rejecterId, Long applicationId) {
		Application application = appRepo.findById(applicationId).orElseThrow(() -> new NotFoundException("Not found application with id: " + applicationId));
		
		if(application.getStatus().equals(ApplicationStatus.REJECTED)) {
			throw new BadRequestException("Application has already been rejected");
		} else if(application.getStatus().equals(ApplicationStatus.APPROVED)) {
			throw new BadRequestException("Application has already been approved");
		}
		
		User controller = userRepo.findById(rejecterId).orElseThrow(() -> new NotFoundException("User not found with id: " + rejecterId));
		application.setStatus(ApplicationStatus.REJECTED);
		application.setReviewed(controller);
		application.setReviewedAt(Instant.now());
		Application rejected = appRepo.save(application);
		return ApplicationMapper.toResponse(rejected);
	}

}
