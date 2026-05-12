package com.lrn.delivery_tracking.service;

import com.lrn.delivery_tracking.dto.request.CourierCreateRequest;
import com.lrn.delivery_tracking.dto.request.CourierUpdateRequest;
import com.lrn.delivery_tracking.dto.response.CourierResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;

public interface CourierService {
	CourierResponse create(CourierCreateRequest coruier);
	CourierResponse getById(Long id);
	PageResponse<CourierResponse> getCouriers(int page, int size, String sortBy);
	CourierResponse updateById(Long id, CourierUpdateRequest req);
}
