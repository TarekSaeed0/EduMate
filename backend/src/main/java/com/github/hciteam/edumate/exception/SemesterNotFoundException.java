package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class SemesterNotFoundException extends ApiException {
	public SemesterNotFoundException() {
		super("SEMESTER_NOT_FOUND", "Semester with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
