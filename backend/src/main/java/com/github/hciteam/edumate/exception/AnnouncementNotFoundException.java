package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class AnnouncementNotFoundException extends ApiException {
	public AnnouncementNotFoundException() {
		super("ANNOUNCEMENT_NOT_FOUND", "Announcement was not found",
				HttpStatus.NOT_FOUND);
	}

	public AnnouncementNotFoundException(Long id) {
		super("ANNOUNCEMENT_NOT_FOUND",
				"Announcement with id " + id + " was not found", HttpStatus.NOT_FOUND);
	}
}
