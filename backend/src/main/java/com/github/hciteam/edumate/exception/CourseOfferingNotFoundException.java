package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseOfferingNotFoundException extends ApiException {
	public CourseOfferingNotFoundException() {
		super("COURSE_REGISTRATION_NOT_FOUND",
				"Course offering with this ID was not found", HttpStatus.NOT_FOUND);
	}
}
