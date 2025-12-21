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
import com.github.hciteam.edumate.dto.TeamJoinRequestDTO;
import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.service.TeamJoinRequestService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/team-join-requests")
public class TeamJoinRequestController {
	private final TeamJoinRequestService requestService;

	public TeamJoinRequestController(TeamJoinRequestService requestService) {
		this.requestService = requestService;
	}

	@GetMapping
	public ResponseEntity<List<TeamJoinRequestDTO>> getRequests(
			@RequestParam(required = false) Long teamId,
			@RequestParam(required = false) Long studentId,
			@RequestParam(required = false) TeamJoinStatus status) {
		return ResponseEntity
				.ok(requestService.getRequests(teamId, studentId, status));
	}

	@PostMapping
	@PreAuthorize("@authorizationService.isStudentSelf(#requestDTO.student.id)")
	public ResponseEntity<TeamJoinRequestDTO> createRequest(
			@Validated(ValidationGroups.Create.class) @RequestBody TeamJoinRequestDTO requestDTO) {
		TeamJoinRequestDTO createdRequest =
				requestService.createRequest(requestDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{requestId}").buildAndExpand(createdRequest.getId()).toUri();

		return ResponseEntity.created(location).body(createdRequest);
	}

	@GetMapping("/{requestId}")
	public ResponseEntity<TeamJoinRequestDTO> getRequest(
			@PathVariable Long requestId) {
		return ResponseEntity.ok(requestService.getRequest(requestId));
	}

	@PostMapping("/{requestId}/accept")
	@PreAuthorize("@authorizationService.isRequestRecipient(#requestId)")
	public ResponseEntity<Void> acceptRequest(@PathVariable Long requestId) {
		requestService.acceptRequest(requestId);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{requestId}/reject")
	@PreAuthorize("@authorizationService.isRequestRecipient(#requestId)")
	public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId) {
		requestService.rejectRequest(requestId);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{requestId}")
	@PreAuthorize("@authorizationService.isRequestSender(#requestId) or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Void> deleteRequest(@PathVariable Long requestId) {
		requestService.deleteRequest(requestId);

		return ResponseEntity.noContent().build();
	}
}
