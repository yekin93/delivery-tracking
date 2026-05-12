package com.lrn.delivery_tracking.mapper;

import com.lrn.delivery_tracking.dto.request.CourierLocationCreateRequest;
import com.lrn.delivery_tracking.dto.response.CourierLocationResponse;
import com.lrn.delivery_tracking.entity.CourierLocation;

public class CourierLocationMapper {
	
	private CourierLocationMapper() {}

	
	public static CourierLocation toEntity(CourierLocationCreateRequest req) {
		CourierLocation location = new CourierLocation();
		location.setLatitude(req.latitude());
		location.setLongitude(req.longitude());
		location.setSpeed(req.speed());
		location.setHeading(req.heading());
		location.setAccuracy(req.accuracy());
		location.setRecordedAt(req.recordedAt());
		return location;
	}
	
	public static CourierLocationResponse toResponse(CourierLocation location) {
		return new CourierLocationResponse(
											location.getId(),
											location.getLatitude(),
											location.getLongitude(),
											location.getRecordedAt());
	}
	
}
