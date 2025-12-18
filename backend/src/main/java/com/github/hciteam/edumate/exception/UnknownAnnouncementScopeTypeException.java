package com.github.hciteam.edumate.exception;

public class UnknownAnnouncementScopeTypeException extends ApiException {
	public UnknownAnnouncementScopeTypeException(String scopeType) {
		super("UNKNOWN_ANNOUNCEMENT_SCOPE_TYPE",
				"Unknown announcement scope type: " + scopeType,
				org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

