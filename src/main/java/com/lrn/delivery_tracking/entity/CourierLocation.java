package com.lrn.delivery_tracking.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "courier_locations")
@Entity
public class CourierLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "courier_id", nullable = false)
	private Courier courier;
	
	@Column(nullable = false, precision = 10, scale = 7)
	private BigDecimal latitude;
	
	@Column(nullable = false, precision = 10, scale = 7)
	private BigDecimal longitude;
	
	@Column(precision = 6, scale = 2)
	private BigDecimal speed;
	
	@Column(precision = 6, scale = 2)
	private BigDecimal heading;
	
	@Column(precision = 6, scale = 2)
	private BigDecimal accuracy;
	
	@Column(name = "recorded_at", nullable = false)
	private Instant recordedAt;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		if(recordedAt == null) recordedAt = now;
	}
	
}
