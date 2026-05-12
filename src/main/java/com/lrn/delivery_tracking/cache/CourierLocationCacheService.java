package com.lrn.delivery_tracking.cache;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lrn.delivery_tracking.dto.response.CourierLocationResponse;

import tools.jackson.databind.ObjectMapper;

@Service
public class CourierLocationCacheService {

	private final Duration LOCATION_TTL = Duration.ofMinutes(10);
	private final StringRedisTemplate stringTemplate;
	private final ObjectMapper objectMapper;
	
	public CourierLocationCacheService(StringRedisTemplate stringTemplate,
										ObjectMapper objectMapper) {
		this.stringTemplate = stringTemplate;
		this.objectMapper = objectMapper;
	}
	
	
	public void saveLatestLocation(Long courierId, CourierLocationResponse location) {
		try {
			String json = objectMapper.writeValueAsString(location);
			stringTemplate.opsForValue().set(generateKey(courierId), json, LOCATION_TTL);
		} catch (Exception ex) {
			//TODO: log
		}
	}
	
	public Optional<CourierLocationResponse> getLatestLocation(Long courierId) {
		String json = stringTemplate.opsForValue().get(generateKey(courierId));
		
		if(json == null) {
			return Optional.empty();
		}
		
		try {
			CourierLocationResponse location = objectMapper.readValue(json, CourierLocationResponse.class);
			return Optional.of(location);
		} catch (Exception ex) {
			//TODO: log
			return Optional.empty();
		}
	}
	
	public void deleteLatestLocation(Long courierId) {
		stringTemplate.delete(generateKey(courierId));
	}
	
	
	private String generateKey(Long courierId) {
		return "courier:" + courierId + ":location";
	}
	
	
}
