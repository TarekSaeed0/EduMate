package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
  public UserAlreadyExistsException() {
    super("USER_ALREADY_EXISTS", "User with this email already exists",
        HttpStatus.CONFLICT);
  }
}
