package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UniversityNotFoundException extends ApiException {
	public UniversityNotFoundException() {
		super("UNIVERSITY_NOT_FOUND", "University was not found",
				HttpStatus.NOT_FOUND);
	}

	public UniversityNotFoundException(Long id) {
		super("UNIVERSITY_NOT_FOUND", "University with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
