package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TeamJoinInviteNotFoundException extends ApiException {
	public TeamJoinInviteNotFoundException() {
		super("TEAM_JOIN_INVITE_NOT_FOUND",
				"TeamJoinInvite with this ID was not found", HttpStatus.NOT_FOUND);
	}
}
