package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentCourseNotFoundException extends ApiException {
	public StudentCourseNotFoundException() {
		super("STUDENT_COURSE_NOT_FOUND",
				"Student course with this ID was not found", HttpStatus.NOT_FOUND);
	}
}
