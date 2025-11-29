package com.github.hciteam.edumate.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.CourseRegistrationDTO;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import com.github.hciteam.edumate.service.CourseRegistrationService;

@RestController
@RequestMapping("/api/registrations")
public class CourseRegistrationController {
	private final CourseRegistrationService registrationService;

	public CourseRegistrationController(
			CourseRegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@GetMapping
	public ResponseEntity<List<CourseRegistrationDTO>> getRegistrations(
			@RequestParam(required = false) Long offeringId,
			@RequestParam(required = false) Long semesterId,
			@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) Long studentId,
			@RequestParam(required = false) CourseRegistrationStatus status) {
		return ResponseEntity.ok(registrationService.getRegistrations(offeringId,
				semesterId, courseId, studentId, status));
	}

	@PostMapping
	public ResponseEntity<CourseRegistrationDTO> createRegistration(
			@RequestBody CourseRegistrationDTO registrationDTO) {
		CourseRegistrationDTO createdRegistration =
				registrationService.createRegistration(registrationDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{registrationId}").buildAndExpand(createdRegistration.getId())
				.toUri();

		return ResponseEntity.created(location).body(createdRegistration);
	}

	@GetMapping("/{registrationId}")
	public ResponseEntity<CourseRegistrationDTO> getRegistration(
			@PathVariable Long registrationId) {
		return ResponseEntity
				.ok(registrationService.getRegistration(registrationId));
	}

	@PutMapping("/{registrationId}")
	public ResponseEntity<CourseRegistrationDTO> updateRegistration(
			@PathVariable Long registrationId,
			@RequestBody CourseRegistrationDTO registrationDTO) {
		return ResponseEntity.ok(registrationService
				.updateRegistration(registrationId, registrationDTO));
	}

	@DeleteMapping("/{registrationId}")
	public ResponseEntity<Void> deleteRegistration(
			@PathVariable Long registrationId) {
		registrationService.deleteRegistration(registrationId);

		return ResponseEntity.noContent().build();
	}
}
