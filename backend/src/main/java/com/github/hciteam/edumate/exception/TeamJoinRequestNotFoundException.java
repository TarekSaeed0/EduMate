package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TeamJoinRequestNotFoundException extends ApiException {
	public TeamJoinRequestNotFoundException() {
		super("TEAM_JOIN_REQUEST_NOT_FOUND",
				"TeamJoinRequest with this ID was not found", HttpStatus.NOT_FOUND);
	}
}
