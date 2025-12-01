package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TeamGroupNotFoundException extends ApiException {
	public TeamGroupNotFoundException() {
		super("TEAM_GROUP_NOT_FOUND", "Team group with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
