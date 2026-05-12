package com.lrn.delivery_tracking.service;

import com.lrn.delivery_tracking.dto.request.CourierLocationCreateRequest;
import com.lrn.delivery_tracking.dto.response.CourierLocationResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;

public interface CourierLocationService {

	CourierLocationResponse create(Long id, CourierLocationCreateRequest req);
	PageResponse<CourierLocationResponse> locations(Long courierId, int page, int size);
	CourierLocationResponse latestLocation(Long courierId);
	
}
