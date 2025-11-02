package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends ApiException {
	public StudentNotFoundException() {
		super("STUDENT_NOT_FOUND", "Student with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
