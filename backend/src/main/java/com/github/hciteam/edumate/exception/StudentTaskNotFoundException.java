package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentTaskNotFoundException extends ApiException {
	public StudentTaskNotFoundException() {
		super("STUDENT_TASK_NOT_FOUND", "Student task with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
