package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TimeSlotNotFoundException extends ApiException {
	public TimeSlotNotFoundException() {
		super("TIME_SLOT_NOT_FOUND", "Time slot was not found",
				HttpStatus.NOT_FOUND);
	}

	public TimeSlotNotFoundException(Long id) {
		super("TIME_SLOT_NOT_FOUND", "Time slot with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
