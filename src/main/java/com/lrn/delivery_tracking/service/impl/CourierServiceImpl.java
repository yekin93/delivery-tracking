package com.lrn.delivery_tracking.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.dto.request.CourierCreateRequest;
import com.lrn.delivery_tracking.dto.request.CourierUpdateRequest;
import com.lrn.delivery_tracking.dto.response.CourierResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;
import com.lrn.delivery_tracking.entity.Courier;
import com.lrn.delivery_tracking.exception.AlreadyExistsException;
import com.lrn.delivery_tracking.exception.NotFoundException;
import com.lrn.delivery_tracking.mapper.CourierMapper;
import com.lrn.delivery_tracking.repository.CourierRepository;
import com.lrn.delivery_tracking.service.CourierService;

@Service
public class CourierServiceImpl implements CourierService {
	
	private final CourierRepository courierRepo;
	
	public CourierServiceImpl(CourierRepository courierRepo) {
		this.courierRepo = courierRepo;
	}

	@Override
	@Transactional
	public CourierResponse create(CourierCreateRequest request) {
		
		if(courierRepo.existsByEmailOrPhone(request.email(), request.phone())) {
			throw new AlreadyExistsException("Courier exist with email: " + request.email() + " OR phone: " + request.phone());
		}
		
		Courier newCourier = courierRepo.save(CourierMapper.toEntity(request));
		return CourierMapper.toResponse(newCourier);
	}

	@Override
	@Transactional(readOnly = true)
	public CourierResponse getById(Long id) {
		Courier courier = courierRepo.findById(id).orElseThrow(() -> new NotFoundException("Courier not found with id: " + id));
		return CourierMapper.toResponse(courier);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<CourierResponse> getCouriers(int page, int size, String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createdAt"));
		Page<Courier> pageCouriers = courierRepo.findAll(pageable);
		
		List<CourierResponse> couriers = pageCouriers
								.getContent()
								.stream()
								.map(CourierMapper::toResponse)
								.toList();
		
		return new PageResponse<>(couriers, pageCouriers.getNumber(), pageCouriers.getSize(), pageCouriers.getTotalElements(), pageCouriers.getTotalPages());
	}

	@Override
	@Transactional
	public CourierResponse updateById(Long id, CourierUpdateRequest req) {
		Courier courier = courierRepo.findById(id).orElseThrow(() -> new NotFoundException("Courier not found with id: " + id));
		
		if(!req.phone().equals(courier.getPhone()) && courierRepo.existsByPhone(req.phone())) {
			throw new AlreadyExistsException(req.phone() + " already in use");
		}
		
		courier.setPhone(req.phone());
		CourierResponse res = CourierMapper.toResponse(courier);
		return res;
	}

}
