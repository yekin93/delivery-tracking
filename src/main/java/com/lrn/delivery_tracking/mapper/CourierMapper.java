package com.lrn.delivery_tracking.mapper;

import com.lrn.delivery_tracking.dto.request.CourierCreateRequest;
import com.lrn.delivery_tracking.dto.response.CourierResponse;
import com.lrn.delivery_tracking.entity.Application;
import com.lrn.delivery_tracking.entity.Courier;

public class CourierMapper {
	
	private CourierMapper() {}

	public static CourierResponse toResponse(Courier courier) {
		return new CourierResponse(
						courier.getId(),
						courier.getName(),
						courier.getSurname(),
						courier.getStatus(),
						courier.getEmail(),
						courier.getPhone(),
						courier.getCreatedAt()
					);
	}
	
	
	public static Courier toEntity(CourierCreateRequest req) {
		Courier courier = new Courier();
		courier.setName(req.name());
		courier.setSurname(req.surname());
		courier.setEmail(req.email());
		courier.setPhone(req.phone());
		return courier;
	}
	
	public static Courier applicationToCourier(Application app) {
		Courier courier = new Courier();
		courier.setName(app.getApplicantName());
		courier.setSurname(app.getApplicantSurname());
		courier.setEmail(app.getEmail());
		courier.setPhone(app.getPhone());
		return courier;
	}
	
}
