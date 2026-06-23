package com.lrn.delivery_tracking.entity;

import java.time.Instant;

import com.lrn.delivery_tracking.enums.RestaurantStatus;

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
@Entity
@Table(name="restaurants")
@Getter
@Setter
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String city;
	
	@Column(name = "postal_code", nullable = false, length = 10)
	private String postalCode;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private RestaurantStatus status;
	
	@Column(name = "modified_at", nullable = false)
	private Instant modifiedAt;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@PrePersist
	public void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.modifiedAt = now;
		if(this.status == null) this.status = RestaurantStatus.ACTIVE;
	}
	
	@PreUpdate
	public void onUpdate() {
		this.modifiedAt = Instant.now();
	}
}
