package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class UserRoleNotFoundException extends ApiException {
	public UserRoleNotFoundException() {
		super("USER_ROLE_NOT_FOUND", "User role with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
