package com.github.hciteam.edumate.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;
import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.service.TeamJoinInviteService;
import com.github.hciteam.edumate.validation.ValidationGroups;

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

	@PostMapping
	@PreAuthorize("@authorizationService.isTeamLeader(#inviteDTO.team.id)")
	public ResponseEntity<TeamJoinInviteDTO> createInvite(
			@Validated(ValidationGroups.Create.class) @RequestBody TeamJoinInviteDTO inviteDTO) {
		TeamJoinInviteDTO createdInvite = inviteService.createInvite(inviteDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{inviteId}").buildAndExpand(createdInvite.getId()).toUri();

		return ResponseEntity.created(location).body(createdInvite);
	}

	@GetMapping("/{inviteId}")
	public ResponseEntity<TeamJoinInviteDTO> getInvite(
			@PathVariable Long inviteId) {
		return ResponseEntity.ok(inviteService.getInvite(inviteId));
	}

	@PostMapping("/{inviteId}/accept")
	@PreAuthorize("@authorizationService.isInviteRecipient(#inviteId)")
	public ResponseEntity<Void> acceptInvite(@PathVariable Long inviteId) {
		inviteService.acceptInvite(inviteId);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{inviteId}/reject")
	@PreAuthorize("@authorizationService.isInviteRecipient(#inviteId)")
	public ResponseEntity<Void> rejectInvite(@PathVariable Long inviteId) {
		inviteService.rejectInvite(inviteId);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{inviteId}")
	@PreAuthorize("@authorizationService.isInviteSender(#inviteId) or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Void> deleteInvite(@PathVariable Long inviteId) {
		inviteService.deleteInvite(inviteId);

		return ResponseEntity.noContent().build();
	}
}
