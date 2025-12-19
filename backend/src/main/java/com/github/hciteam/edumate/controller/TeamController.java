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
import com.github.hciteam.edumate.dto.TeamDTO;
import com.github.hciteam.edumate.model.TeamStatus;
import com.github.hciteam.edumate.service.TeamService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping
	public ResponseEntity<List<TeamDTO>> getTeams(
			@RequestParam(required = false) Long groupId,
			@RequestParam(required = false) Long leaderId,
			@RequestParam(required = false) Long memberId,
			@RequestParam(required = false) TeamStatus status) {
		return ResponseEntity
				.ok(teamService.getTeams(groupId, leaderId, memberId, status));
	}

	@PostMapping
	@PreAuthorize("@authorizationService.isStudentSelf(#teamDTO.leader.id) or hasRole('COORDINATOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<TeamDTO> createTeam(
			@Validated(ValidationGroups.Create.class) @RequestBody TeamDTO teamDTO) {
		TeamDTO createdTeam = teamService.createTeam(teamDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{teamId}").buildAndExpand(createdTeam.getId()).toUri();

		return ResponseEntity.created(location).body(createdTeam);
	}

	@GetMapping("/{teamId}")
	public ResponseEntity<TeamDTO> getTeam(@PathVariable Long teamId) {
		return ResponseEntity.ok(teamService.getTeam(teamId));
	}

	@DeleteMapping("/{teamId}")
	@PreAuthorize("@authorizationService.isTeamLeader(#teamId) or hasRole('COORDINATOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
		teamService.deleteTeam(teamId);

		return ResponseEntity.noContent().build();
	}
}
