package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseRegistrationAlreadyExistsException extends ApiException {
	public CourseRegistrationAlreadyExistsException() {
		super("COURSE_REGISTRATIONA_ALREADY_EXISTS",
				"Course registration with this ID already exists", HttpStatus.CONFLICT);
	}
}
