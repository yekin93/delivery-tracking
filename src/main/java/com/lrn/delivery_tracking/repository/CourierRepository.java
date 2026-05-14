package com.lrn.delivery_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrn.delivery_tracking.entity.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {

	boolean existsByEmailOrPhone(String email, String phone);
	boolean existsByPhone(String phone);
	
	boolean existsByUserId(Long userId);
}
