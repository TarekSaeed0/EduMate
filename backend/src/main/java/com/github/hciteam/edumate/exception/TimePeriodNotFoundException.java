package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TimePeriodNotFoundException extends ApiException {
	public TimePeriodNotFoundException() {
		super("TIME_PERIOD_NOT_FOUND", "Time period was not found",
				HttpStatus.NOT_FOUND);
	}

	public TimePeriodNotFoundException(Long id) {
		super("TIME_PERIOD_NOT_FOUND",
				"Time period with id " + id + " was not found", HttpStatus.NOT_FOUND);
	}
}
