package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class AnnouncementScopeNotFoundException extends ApiException {
	public AnnouncementScopeNotFoundException() {
		super("ANNOUNCEMENT_SCOPE_NOT_FOUND", "Announcement scope was not found",
				HttpStatus.NOT_FOUND);
	}

	public AnnouncementScopeNotFoundException(String type, Long id) {
		super("ANNOUNCEMENT_SCOPE_NOT_FOUND", "Announcement scope with type " + type
				+ " and id " + id + " was not found", HttpStatus.NOT_FOUND);
	}
}
