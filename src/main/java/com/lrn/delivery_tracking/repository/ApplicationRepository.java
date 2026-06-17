package com.lrn.delivery_tracking.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lrn.delivery_tracking.entity.Application;
import com.lrn.delivery_tracking.enums.ApplicationStatus;
import com.lrn.delivery_tracking.enums.ApplicationType;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	Optional<Application> findById(Long id);
	
	Page<Application> findByTypeAndStatus(ApplicationType type, ApplicationStatus status, Pageable pageable);
}
