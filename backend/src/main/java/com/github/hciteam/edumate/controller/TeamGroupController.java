package com.github.hciteam.edumate.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.TeamGroupDTO;
import com.github.hciteam.edumate.service.TeamGroupService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/team-groups")
public class TeamGroupController {
	private final TeamGroupService groupService;

	public TeamGroupController(TeamGroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping
	public ResponseEntity<List<TeamGroupDTO>> getGroups() {
		return ResponseEntity.ok(groupService.getGroups());
	}

	@PostMapping
	public ResponseEntity<TeamGroupDTO> createGroup(
			@Validated(ValidationGroups.Create.class) @RequestBody TeamGroupDTO groupDTO) {
		TeamGroupDTO createdGroup = groupService.createGroup(groupDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{groupId}").buildAndExpand(createdGroup.getId()).toUri();

		return ResponseEntity.created(location).body(createdGroup);
	}

	@GetMapping("/{groupId}")
	public ResponseEntity<TeamGroupDTO> getGroup(@PathVariable Long groupId) {
		return ResponseEntity.ok(groupService.getGroup(groupId));
	}

	@PutMapping("/{groupId}")
	public ResponseEntity<TeamGroupDTO> updateGroup(@PathVariable Long groupId,
			@Validated(ValidationGroups.Update.class) @RequestBody TeamGroupDTO groupDTO) {
		return ResponseEntity.ok(groupService.updateGroup(groupId, groupDTO));
	}

	@DeleteMapping("/{groupId}")
	public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
		groupService.deleteGroup(groupId);

		return ResponseEntity.noContent().build();
	}
}
