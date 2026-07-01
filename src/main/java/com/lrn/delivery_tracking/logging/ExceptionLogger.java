package com.lrn.delivery_tracking.logging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ExceptionLogger {

	private static final Logger log = LoggerFactory.getLogger(ExceptionLogger.class);
	
	public String logUnexpected(HttpServletRequest req, Exception ex) {
		String errorId = UUID.randomUUID().toString();
		String reqId = MDC.get("requestId");
		log.error("""
				\n
	                ==================== UNEXPECTED ERROR ====================
	                RequestId : {}
					ErrorId   : {}
					Method    : {}
					URL       : {}
					Message   : {}
					==========================================================
				""",
				reqId,
				errorId,
				req.getMethod(),
				req.getRequestURI(),
				ex.getMessage(),
				ex);
		return errorId;
	}
	
	public void logBusiness(HttpServletRequest req, Exception ex) {
		
		log.warn("""
				\n
	                ==================== BUSINESS ERROR ====================
	                RequestId : {}
					Method    : {}
					URL       : {}
					Message   : {}
					==========================================================
				""",
				MDC.get("requestId"),
				req.getMethod(),
				req.getRequestURI(),
				ex.getMessage()
				);
	}
}
