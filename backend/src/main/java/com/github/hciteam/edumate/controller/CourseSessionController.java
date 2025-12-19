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
import com.github.hciteam.edumate.dto.CourseSessionDTO;
import com.github.hciteam.edumate.service.CourseSessionService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/sessions")
public class CourseSessionController {
	private final CourseSessionService sessionService;

	public CourseSessionController(CourseSessionService sessionService) {
		this.sessionService = sessionService;
	}

	@GetMapping
	public ResponseEntity<List<CourseSessionDTO>> getSessions() {
		return ResponseEntity.ok(sessionService.getSessions());
	}

	@PostMapping
	public ResponseEntity<CourseSessionDTO> createSession(
			@Validated(ValidationGroups.Create.class) @RequestBody CourseSessionDTO sessionDTO) {
		CourseSessionDTO createdSession = sessionService.createSession(sessionDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{sessionId}").buildAndExpand(createdSession.getId()).toUri();

		return ResponseEntity.created(location).body(createdSession);
	}

	@GetMapping("/{sessionId}")
	public ResponseEntity<CourseSessionDTO> getSession(
			@PathVariable Long sessionId) {
		return ResponseEntity.ok(sessionService.getSession(sessionId));
	}

	@PutMapping("/{sessionId}")
	public ResponseEntity<CourseSessionDTO> updateSession(
			@PathVariable Long sessionId,
			@Validated(ValidationGroups.Update.class) @RequestBody CourseSessionDTO sessionDTO) {
		return ResponseEntity
				.ok(sessionService.updateSession(sessionId, sessionDTO));
	}

	@DeleteMapping("/{sessionId}")
	public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
		sessionService.deleteSession(sessionId);

		return ResponseEntity.noContent().build();
	}
}
