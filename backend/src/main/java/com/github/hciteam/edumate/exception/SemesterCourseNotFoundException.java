package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class SemesterCourseNotFoundException extends ApiException {
	public SemesterCourseNotFoundException() {
		super("SEMESTER_COURSE_NOT_FOUND",
				"Semester course with this ID was not found", HttpStatus.NOT_FOUND);
	}
}
