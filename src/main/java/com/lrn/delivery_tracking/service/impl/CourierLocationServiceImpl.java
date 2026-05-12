package com.lrn.delivery_tracking.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lrn.delivery_tracking.cache.CourierLocationCacheService;
import com.lrn.delivery_tracking.dto.request.CourierLocationCreateRequest;
import com.lrn.delivery_tracking.dto.response.CourierLocationResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;
import com.lrn.delivery_tracking.entity.Courier;
import com.lrn.delivery_tracking.entity.CourierLocation;
import com.lrn.delivery_tracking.exception.NotFoundException;
import com.lrn.delivery_tracking.mapper.CourierLocationMapper;
import com.lrn.delivery_tracking.repository.CourierLocationRepository;
import com.lrn.delivery_tracking.repository.CourierRepository;
import com.lrn.delivery_tracking.service.CourierLocationService;

@Service
public class CourierLocationServiceImpl implements CourierLocationService {

	private final CourierLocationRepository locationRepo;
	private final CourierRepository courierRepo;
	private final CourierLocationCacheService cache;
	
	
	public CourierLocationServiceImpl(CourierLocationRepository locationRepo,
										CourierRepository courierRepo,
										CourierLocationCacheService cache) {
		this.locationRepo = locationRepo;
		this.courierRepo = courierRepo;
		this.cache = cache;
	}
	
	
	@Override
	@Transactional
	public CourierLocationResponse create(Long id, CourierLocationCreateRequest req) {
		Courier courier = courierRepo
							.findById(id)
							.orElseThrow(() -> new NotFoundException("Courier not found with id: " + id));
		
		CourierLocation location = CourierLocationMapper.toEntity(req);
		location.setCourier(courier);
		
		CourierLocation newLocation = locationRepo.save(location);
		CourierLocationResponse resLocation = CourierLocationMapper.toResponse(newLocation);
		cache.saveLatestLocation(courier.getId(), resLocation);
		return resLocation;
	}


	@Override
	@Transactional(readOnly = true)
	public PageResponse<CourierLocationResponse> locations(Long courierId, int page, int size) {
		Courier courier = courierRepo.findById(courierId).orElseThrow(() -> new NotFoundException("Courier not found with id: " + courierId));
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "recordedAt"));
		Page<CourierLocation> locations = locationRepo.findByCourierId(courier.getId(), pageable);
		List<CourierLocationResponse> locationsRes = locations
														.getContent()
														.stream()
														.map(CourierLocationMapper::toResponse)
														.toList();
		return new PageResponse<CourierLocationResponse>(locationsRes, locations.getNumber(), locations.getSize(), locations.getTotalElements(), locations.getTotalPages());
	}


	@Override
	@Transactional(readOnly = true)
	public CourierLocationResponse latestLocation(Long courierId) {
		courierRepo.findById(courierId)
				.orElseThrow(() -> new NotFoundException("Courier not found with id: " + courierId));
		CourierLocation latesLocation = locationRepo
								.findTopByCourierIdOrderByRecordedAtDesc(courierId)
								.orElseThrow(() -> new NotFoundException("Location not found for courier id: " + courierId));
		return CourierLocationMapper.toResponse(latesLocation);
	}

}
