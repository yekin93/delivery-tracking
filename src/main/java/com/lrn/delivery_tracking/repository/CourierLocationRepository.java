package com.lrn.delivery_tracking.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lrn.delivery_tracking.entity.CourierLocation;

public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {

	Page<CourierLocation> findByCourierId(Long courierId, Pageable pageable);
	
	Optional<CourierLocation> findTopByCourierIdOrderByRecordedAtDesc(Long courierId);
}
