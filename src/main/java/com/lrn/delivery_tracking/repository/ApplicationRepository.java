package com.lrn.delivery_tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrn.delivery_tracking.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
