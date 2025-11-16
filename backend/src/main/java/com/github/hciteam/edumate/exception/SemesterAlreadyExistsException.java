package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class SemesterAlreadyExistsException extends ApiException {
	public SemesterAlreadyExistsException() {
		super("SEMESTER_ALREADY_EXISTS", "Semester with this ID already exists",
				HttpStatus.CONFLICT);
	}
}
