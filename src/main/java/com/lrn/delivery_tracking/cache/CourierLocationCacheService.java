package com.lrn.delivery_tracking.cache;

import java.time.Duration;

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
			String key = "courier:" + courierId + ":location";
			String json = objectMapper.writeValueAsString(location);
			stringTemplate.opsForValue().set(key, json, LOCATION_TTL);
		} catch (Exception ex) {
			//TODO: log
		}
	}
	
	
	
}
