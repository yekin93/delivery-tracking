package com.lrn.delivery_tracking.service;

import com.lrn.delivery_tracking.dto.request.ApplicationCreateRequest;
import com.lrn.delivery_tracking.dto.response.ApplicationResponse;

public interface ApplicationService {

	ApplicationResponse create(Long userId, ApplicationCreateRequest req);
	
}
