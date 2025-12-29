package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TeamGroupNotFoundException extends ApiException {
	public TeamGroupNotFoundException() {
		super("TEAM_GROUP_NOT_FOUND", "Team group was not found",
				HttpStatus.NOT_FOUND);
	}

	public TeamGroupNotFoundException(Long id) {
		super("TEAM_GROUP_NOT_FOUND", "Team group with id " + id + " was not found",
				HttpStatus.NOT_FOUND);
	}
}
