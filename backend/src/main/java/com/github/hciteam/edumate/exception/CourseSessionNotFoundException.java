package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseSessionNotFoundException extends ApiException {
	public CourseSessionNotFoundException() {
		super("COURSE_SESSION_NOT_FOUND", "Course session was not found",
				HttpStatus.NOT_FOUND);
	}

	public CourseSessionNotFoundException(Long id) {
		super("COURSE_SESSION_NOT_FOUND",
				"Course session with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
