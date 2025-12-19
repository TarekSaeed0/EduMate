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
import com.github.hciteam.edumate.dto.SessionDTO;
import com.github.hciteam.edumate.service.SessionService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
	private final SessionService sessionService;

	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@GetMapping
	public ResponseEntity<List<SessionDTO>> getSessions() {
		return ResponseEntity.ok(sessionService.getSessions());
	}

	@PostMapping
	public ResponseEntity<SessionDTO> createSession(
			@Validated(ValidationGroups.Create.class) @RequestBody SessionDTO sessionDTO) {
		SessionDTO createdSession = sessionService.createSession(sessionDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{sessionId}").buildAndExpand(createdSession.getId()).toUri();

		return ResponseEntity.created(location).body(createdSession);
	}

	@GetMapping("/{sessionId}")
	public ResponseEntity<SessionDTO> getSession(@PathVariable Long sessionId) {
		return ResponseEntity.ok(sessionService.getSession(sessionId));
	}

	@PutMapping("/{sessionId}")
	public ResponseEntity<SessionDTO> updateSession(@PathVariable Long sessionId,
			@Validated(ValidationGroups.Update.class) @RequestBody SessionDTO sessionDTO) {
		return ResponseEntity
				.ok(sessionService.updateSession(sessionId, sessionDTO));
	}

	@DeleteMapping("/{sessionId}")
	public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
		sessionService.deleteSession(sessionId);

		return ResponseEntity.noContent().build();
	}
}
