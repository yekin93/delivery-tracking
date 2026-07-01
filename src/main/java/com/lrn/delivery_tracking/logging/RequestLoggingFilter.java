package com.lrn.delivery_tracking.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lrn.delivery_tracking.security.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);
	private final ObjectMapper mapper;
	
	private static final Set<String> SENSITIVE_FIELDS = Set.of(
				"password",
				"token",
				"accessToken",
				"refreshToken",
				"authorization"
			);

	public RequestLoggingFilter(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		if(!request.getRequestURI().startsWith("/api")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
		
		Instant start = Instant.now();
		ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request, 1024 * 1024);
		String requestId = UUID.randomUUID().toString();
		MDC.put("requestId", requestId);
		MDC.put("ip", getClientIp(request));
		//MDC.put("user", getCurrentUser());
		try {
			filterChain.doFilter(cachingRequest, response);
		} finally {
			long durationMs = Duration.between(start, Instant.now()).toMillis();
					
			log.info("""
					\n
	                ==================== HTTP REQUEST ====================
	                RequestId : {}
	                Method    : {}
	                URL       : {}{}
	                Status    : {}
	                Duration  : {} ms
	                User      : {}
	                IP        : {}
	                Payload   : {}
	                =====================================================
	                """,
	                requestId,
					request.getMethod(),
					request.getRequestURI(),
					getQueryString(request),
					response.getStatus(),
					durationMs,
					getCurrentUser(),
					getClientIp(request),
					maskSensitiveFields(getRequestBody(cachingRequest))
					);
			MDC.clear();
		}
	}
	
	private String maskSensitiveFields(String payload) {
		if(payload == null || payload.isBlank()) {
			return "";
		}
		
		try {
			JsonNode root = mapper.readTree(payload);
			maskJsonNode(root);
			return mapper.writerWithDefaultPrettyPrinter()
	                .writeValueAsString(root);
		} catch (Exception ex) {
			return payload;
		}
	}
	
	private void maskJsonNode(JsonNode node) {
		if(node == null) {
			return;
		}
		
		if(node.isObject()) {
			ObjectNode object = (ObjectNode) node;
			
			object.fieldNames().forEachRemaining(fieldName -> {
				JsonNode child = object.get(fieldName);
				if(isSensitiveField(fieldName)) {
					object.put(fieldName, "********");
				} else {
					maskJsonNode(child);
				}
			});
			
		} else if (node.isArray()) {
			node.forEach(this::maskJsonNode);
		}
	}
	
	private boolean isSensitiveField(String fieldName) {
		return SENSITIVE_FIELDS
				.stream()
				.anyMatch(field -> field.equalsIgnoreCase(fieldName));
	}
	
	private String getQueryString(HttpServletRequest request) {
        String query = request.getQueryString();
        return query == null ? "" : "?" + query;
    }
	
	private String getRequestBody(ContentCachingRequestWrapper request) {
	    byte[] content = request.getContentAsByteArray();

	    if (content.length == 0) {
	        return "";
	    }

	    return new String(content, StandardCharsets.UTF_8);
	}
	
	private String getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth == null || !auth.isAuthenticated()) {
			return "anonymous";
		}
		
		Object principal = auth.getPrincipal();
		
		if(principal instanceof CustomUserDetails user) {
			return user.getUsername();
		}
		
		if(principal instanceof String value) {
			if("anonymousUser".equals(value)) {
				return "anonymous";
			}
			return value;
		}
		
		return "unkown";
	}

	private String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
