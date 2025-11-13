package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends ApiException {
	public CourseNotFoundException() {
		super("COURSE_NOT_FOUND", "Course with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
