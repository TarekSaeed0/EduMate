package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseAlreadyExistsException extends ApiException {
	public CourseAlreadyExistsException() {
		super("COURSE_ALREADY_EXISTS", "Course with this ID already exists",
				HttpStatus.CONFLICT);
	}
}
