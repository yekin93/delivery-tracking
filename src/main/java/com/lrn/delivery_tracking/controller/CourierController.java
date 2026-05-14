package com.lrn.delivery_tracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrn.delivery_tracking.dto.request.CourierCreateRequest;
import com.lrn.delivery_tracking.dto.request.CourierLocationCreateRequest;
import com.lrn.delivery_tracking.dto.request.CourierUpdateRequest;
import com.lrn.delivery_tracking.dto.response.CourierLocationResponse;
import com.lrn.delivery_tracking.dto.response.CourierResponse;
import com.lrn.delivery_tracking.dto.response.GlobalResponse;
import com.lrn.delivery_tracking.dto.response.PageResponse;
import com.lrn.delivery_tracking.security.CustomUserDetails;
import com.lrn.delivery_tracking.service.CourierLocationService;
import com.lrn.delivery_tracking.service.CourierService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    
	private final CourierService courierService;
	private final CourierLocationService locationService;
	
	public CourierController(CourierService courierService, CourierLocationService locationService) {
		this.courierService = courierService;
		this.locationService = locationService;
	}
	
	@PostMapping
	public ResponseEntity<GlobalResponse<CourierResponse>> createCourier(@Valid @RequestBody CourierCreateRequest req){
		CourierResponse courier = courierService.create(req);
		
		return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(new GlobalResponse<>(courier,
												HttpStatus.CREATED.value(),
												"Courier successfully created"));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GlobalResponse<CourierResponse>> getById(@PathVariable Long id){
		CourierResponse courier = courierService.getById(id);
		return ResponseEntity
					.ok(new GlobalResponse<>(courier,
												HttpStatus.OK.value(),
												"Courier successfully fetched"));
	}
	
	@GetMapping
	public ResponseEntity<GlobalResponse<PageResponse<CourierResponse>>> getCouriers(
														@RequestParam(defaultValue = "50") @Min(0) @Max(50) int size,
														@RequestParam(defaultValue = "0") @Min(0) @Max(9999) int page,
														@RequestParam(defaultValue = "desc") String sortBy
									){
		PageResponse<CourierResponse> couriers = courierService.getCouriers(page, size, sortBy);
		return ResponseEntity.ok(new GlobalResponse<>(couriers, HttpStatus.OK.value(), "couriers successfully fetched"));
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<GlobalResponse<CourierResponse>> updateCourier(@PathVariable Long id, @Valid @RequestBody CourierUpdateRequest req) {
		CourierResponse updated = courierService.updateById(id, req);
		return ResponseEntity
					.ok(new GlobalResponse<>(updated, HttpStatus.OK.value(), "Courier successfully updated"));
	}
	
	@PostMapping("/{id}/locations")
	public ResponseEntity<GlobalResponse<CourierLocationResponse>> createLocation(@PathVariable Long id, @Valid @RequestBody CourierLocationCreateRequest req) {
		
		CourierLocationResponse location = locationService.create(id, req);
		return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(new GlobalResponse<>(location, HttpStatus.CREATED.value(), "location successfully added"));
	}
	
	@GetMapping("/{courierId}/locations")
	public ResponseEntity<GlobalResponse<PageResponse<CourierLocationResponse>>> getLocationsByCourierId(
				@PathVariable Long courierId,
				@RequestParam(defaultValue = "0") @Min(0) int page,
				@RequestParam(defaultValue = "30") @Min(0) @Max(30) int size
			){
		PageResponse<CourierLocationResponse> locations = locationService.locations(courierId, page, size);
		
		return ResponseEntity.ok(new GlobalResponse<>(locations, HttpStatus.OK.value(), "Locations successfully fetched"));
	}
	
	@GetMapping("/{courierId}/locations/latest")
	public ResponseEntity<GlobalResponse<CourierLocationResponse>> getLatestLocation(@PathVariable Long courierId){
		CourierLocationResponse latestLocation = locationService.latestLocation(courierId);
		return ResponseEntity.ok(new GlobalResponse<>(latestLocation, HttpStatus.OK.value(), "Latest location successfully fetched"));
	}
	
	@GetMapping("/me")
	public ResponseEntity<GlobalResponse<String>> me(@AuthenticationPrincipal CustomUserDetails user){
		return ResponseEntity.ok(new GlobalResponse<>(user.getUsername(), HttpStatus.OK.value(), "loggedin user fetched successfully"));
	}
	
}
