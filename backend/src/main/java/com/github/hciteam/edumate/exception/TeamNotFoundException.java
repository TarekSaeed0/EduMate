package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends ApiException {
	public TeamNotFoundException() {
		super("TEAM_NOT_FOUND", "Team with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
