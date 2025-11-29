package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseRegistrationNotFoundException extends ApiException {
	public CourseRegistrationNotFoundException() {
		super("COURSE_REGISTRATION_NOT_FOUND",
				"Course registration with this ID was not found", HttpStatus.NOT_FOUND);
	}
}
