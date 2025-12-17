package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseOfferingNotFoundException extends ApiException {
	public CourseOfferingNotFoundException() {
		super("COURSE_OFFERING_NOT_FOUND", "Course offering was not found",
				HttpStatus.NOT_FOUND);
	}

	public CourseOfferingNotFoundException(Long id) {
		super("COURSE_OFFERING_NOT_FOUND",
				"Course offering with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
