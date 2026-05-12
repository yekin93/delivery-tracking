package com.lrn.delivery_tracking.entity;

import java.time.Instant;

import com.lrn.delivery_tracking.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@Column(name = "password_hash", length = 255)
	private String passwordHash;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private Role role;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.updatedAt = now;
		if(this.role == null) {
			this.role = Role.CUSTOMER;
		}
		this.enabled = true;
	}
	
	@PreUpdate
	private void onUpdate() {
		this.updatedAt = Instant.now();
	}
}
