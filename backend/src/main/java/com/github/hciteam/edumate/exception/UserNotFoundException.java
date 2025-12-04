package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
	public UserNotFoundException() {
		super("USER_NOT_FOUND", "User was not found", HttpStatus.NOT_FOUND);
	}

	public UserNotFoundException(Long id) {
		super("USER_NOT_FOUND", "User with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}

	public UserNotFoundException(String email) {
		super("USER_NOT_FOUND", "User with email " + email + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
