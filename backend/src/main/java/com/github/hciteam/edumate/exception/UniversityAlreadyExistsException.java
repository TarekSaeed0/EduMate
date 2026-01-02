package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UniversityAlreadyExistsException extends ApiException {
	public UniversityAlreadyExistsException() {
		super("UNIVERSITY_ALREADY_EXISTS", "University already exists",
				HttpStatus.CONFLICT);
	}

	public UniversityAlreadyExistsException(String name) {
		super("UNIVERSITY_ALREADY_EXISTS",
				"University with name " + name + " already exists",
				HttpStatus.CONFLICT);
	}
}
