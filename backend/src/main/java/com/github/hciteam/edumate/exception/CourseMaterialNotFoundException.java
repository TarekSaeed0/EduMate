package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class CourseMaterialNotFoundException extends ApiException {
	public CourseMaterialNotFoundException() {
		super("COURSE_MATERIAL_NOT_FOUND", "Course material was not found",
				HttpStatus.NOT_FOUND);
	}

	public CourseMaterialNotFoundException(Long id) {
		super("COURSE_MATERIAL_NOT_FOUND",
				"Course material with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
