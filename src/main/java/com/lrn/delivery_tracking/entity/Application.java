package com.lrn.delivery_tracking.entity;

import java.time.Instant;

import com.lrn.delivery_tracking.enums.ApplicationStatus;
import com.lrn.delivery_tracking.enums.ApplicationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="applications")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 50)
	private ApplicationType type;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 30)
	private ApplicationStatus status;
	
	@Column(name = "applicant_name", length = 100)
	private String applicantName;
	
	@Column(name = "business_name", length = 100)
	private String businessName;
	
	@Column(nullable = false, length = 150)
	private String email;
	
	@Column(nullable = false, length = 30)
	private String phone;
	
	private String message;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewed_by")
	private User reviewed;
	
	@Column(name = "reviewed_at")
	private Instant reviewedAt;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	
	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.updatedAt = now;
		if(this.status == null) {
			this.status = ApplicationStatus.PENDING;
		}
	}
	
	@PreUpdate
	private void onUpdate() {
		this.updatedAt = Instant.now();
	}
}
