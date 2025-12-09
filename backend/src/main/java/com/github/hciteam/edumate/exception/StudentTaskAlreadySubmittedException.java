package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentTaskAlreadySubmittedException extends ApiException {
  public StudentTaskAlreadySubmittedException() {
    super("STUDENT_TASK_ALREADY_SUBMITTED", "Student task is already submitted",
        HttpStatus.BAD_REQUEST);
  }

  public StudentTaskAlreadySubmittedException(String message) {
    super("STUDENT_TASK_ALREADY_SUBMITTED", message, HttpStatus.BAD_REQUEST);
  }
}
