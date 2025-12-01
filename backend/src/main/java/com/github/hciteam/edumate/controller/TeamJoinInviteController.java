package com.github.hciteam.edumate.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;
import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.service.TeamJoinInviteService;

@RestController
@RequestMapping("/api/team-join-invites")
public class TeamJoinInviteController {
	private final TeamJoinInviteService inviteService;

	public TeamJoinInviteController(TeamJoinInviteService inviteService) {
		this.inviteService = inviteService;
	}

	@GetMapping
	public ResponseEntity<List<TeamJoinInviteDTO>> getInvites(
			@RequestParam(required = false) Long teamId,
			@RequestParam(required = false) Long studentId,
			@RequestParam(required = false) TeamJoinStatus status) {
		return ResponseEntity
				.ok(inviteService.getInvites(teamId, studentId, status));
	}

	@GetMapping("/{inviteId}")
	public ResponseEntity<TeamJoinInviteDTO> getInvite(
			@PathVariable Long inviteId) {
		return ResponseEntity.ok(inviteService.getInvite(inviteId));
	}
}
