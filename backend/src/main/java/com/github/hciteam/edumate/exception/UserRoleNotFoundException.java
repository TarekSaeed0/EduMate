package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UserRoleNotFoundException extends ApiException {
	public UserRoleNotFoundException() {
		super("USER_ROLE_NOT_FOUND", "User role was not found",
				HttpStatus.NOT_FOUND);
	}

	public UserRoleNotFoundException(String roleName) {
		super("USER_ROLE_NOT_FOUND", "User role " + roleName + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
