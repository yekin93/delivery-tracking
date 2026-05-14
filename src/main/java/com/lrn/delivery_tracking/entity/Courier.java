package com.lrn.delivery_tracking.entity;

import java.time.Instant;

import com.lrn.delivery_tracking.enums.CourierStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Table(name = "couriers")
@Entity
public class Courier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String surname;
	
	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@Column(nullable = false, unique = true, length = 30)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private CourierStatus status;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
	
	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.updatedAt = now;
		if(this.status == null) {
			this.status = CourierStatus.OFFLINE;
		}
	}
	
	@PreUpdate
	private void onUpdate() {
		this.updatedAt = Instant.now();
	}
}
