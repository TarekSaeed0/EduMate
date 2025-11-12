package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentCourseAlreadyExistsException extends ApiException {
	public StudentCourseAlreadyExistsException() {
		super("STUDENT_COURSE_ALREADY_EXISTS",
				"Student is already enrolled in this course", HttpStatus.CONFLICT);
	}
}
