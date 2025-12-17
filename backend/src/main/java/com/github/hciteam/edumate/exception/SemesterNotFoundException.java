package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class SemesterNotFoundException extends ApiException {
	public SemesterNotFoundException() {
		super("SEMESTER_NOT_FOUND", "Semester was not found", HttpStatus.NOT_FOUND);
	}

	public SemesterNotFoundException(Long id) {
		super("SEMESTER_NOT_FOUND", "Semester with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
