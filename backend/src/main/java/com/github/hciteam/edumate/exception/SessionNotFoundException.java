package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends ApiException {
	public SessionNotFoundException() {
		super("SESSION_NOT_FOUND", "Session was not found", HttpStatus.NOT_FOUND);
	}

	public SessionNotFoundException(Long id) {
		super("SESSION_NOT_FOUND", "Session with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
