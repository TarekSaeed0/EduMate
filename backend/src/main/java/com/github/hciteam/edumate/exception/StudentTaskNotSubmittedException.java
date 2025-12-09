package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class StudentTaskNotSubmittedException extends ApiException {
  public StudentTaskNotSubmittedException() {
    super("STUDENT_TASK_NOT_SUBMITTED", "Student task is not submitted",
        HttpStatus.BAD_REQUEST);
  }

  public StudentTaskNotSubmittedException(String message) {
    super("STUDENT_TASK_NOT_SUBMITTED", message, HttpStatus.BAD_REQUEST);
  }
}
