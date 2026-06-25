package com.lrn.delivery_tracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrn.delivery_tracking.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String name);
}
