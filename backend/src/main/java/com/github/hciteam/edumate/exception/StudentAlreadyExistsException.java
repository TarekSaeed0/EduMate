package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentAlreadyExistsException extends ApiException {
  public StudentAlreadyExistsException() {
    super("STUDENT_ALREADY_EXISTS", "Student with this ID already exists",
        HttpStatus.CONFLICT);
  }
}
