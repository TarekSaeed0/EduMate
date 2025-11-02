package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentCourseAlreadyExists extends ApiException {
	public StudentCourseAlreadyExists() {
		super("STUDENT_COURSE_ALREADY_EXISTS",
				"Student is already enrolled in this course", HttpStatus.CONFLICT);
	}
}
