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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.service.CourseOfferingService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/offerings")
public class CourseOfferingController {
	private final CourseOfferingService offeringService;

	public CourseOfferingController(CourseOfferingService offeringService) {
		this.offeringService = offeringService;
	}

	@GetMapping
	public ResponseEntity<List<CourseOfferingDTO>> getOfferings(
			@RequestParam(required = false) Long semesterId,
			@RequestParam(required = false) Long courseId) {
		return ResponseEntity
				.ok(offeringService.getOfferings(semesterId, courseId));
	}

	@PostMapping
	public ResponseEntity<CourseOfferingDTO> createOffering(
			@Validated(ValidationGroups.Create.class) @RequestBody CourseOfferingDTO offeringDTO) {
		CourseOfferingDTO createdOffering =
				offeringService.createOffering(offeringDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{offeringId}").buildAndExpand(createdOffering.getId()).toUri();

		return ResponseEntity.created(location).body(createdOffering);
	}

	@GetMapping("/{offeringId}")
	public ResponseEntity<CourseOfferingDTO> getOffering(
			@PathVariable Long offeringId) {
		return ResponseEntity.ok(offeringService.getOffering(offeringId));
	}

	@DeleteMapping("/{offeringId}")
	public ResponseEntity<Void> deleteOffering(@PathVariable Long offeringId) {
		offeringService.deleteOffering(offeringId);

		return ResponseEntity.noContent().build();
	}
}
