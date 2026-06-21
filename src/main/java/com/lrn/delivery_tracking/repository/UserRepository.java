package com.lrn.delivery_tracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lrn.delivery_tracking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	
	@Query("""
			SELECT u FROM User u
			LEFT JOIN FETCH u.roles
			WHERE u.email = :email
			""")
	Optional<User> findByEmailWithRoles(String email);
}
