package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends ApiException {
	public RoleNotFoundException() {
		super("ROLE_NOT_FOUND", "Role was not found", HttpStatus.NOT_FOUND);
	}

	public RoleNotFoundException(String roleName) {
		super("ROLE_NOT_FOUND", "Role " + roleName + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
