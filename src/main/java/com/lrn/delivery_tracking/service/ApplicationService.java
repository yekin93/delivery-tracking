package com.lrn.delivery_tracking.service;

import com.lrn.delivery_tracking.dto.request.ApplicationCreateRequest;
import com.lrn.delivery_tracking.dto.response.ApplicationResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;
import com.lrn.delivery_tracking.enums.ApplicationStatus;
import com.lrn.delivery_tracking.enums.ApplicationType;

public interface ApplicationService {

	ApplicationResponse create(Long userId, ApplicationCreateRequest req);
	PageResponse<ApplicationResponse> getApplications(ApplicationStatus status, ApplicationType type, int size, int page, String sortBy);
	ApplicationResponse getById(Long id);
	ApplicationResponse approveApplication(Long approverId, Long applicationId);
	ApplicationResponse rejectApplication(Long rejecterId, Long applicationId);
}
