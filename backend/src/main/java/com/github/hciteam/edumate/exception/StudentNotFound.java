package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentNotFound extends ApiException {
	public StudentNotFound() {
		super("STUDENT_NOT_FOUND", "Student with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
