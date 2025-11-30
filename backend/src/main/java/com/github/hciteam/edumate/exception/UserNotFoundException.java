package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
	public UserNotFoundException() {
		super("USER_NOT_FOUND", "User with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
