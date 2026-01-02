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
import com.github.hciteam.edumate.dto.UniversityDTO;
import com.github.hciteam.edumate.service.UniversityService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {
	private final UniversityService universityService;

	public UniversityController(UniversityService universityService) {
		this.universityService = universityService;
	}

	@GetMapping
	public ResponseEntity<List<UniversityDTO>> getUniversitys() {
		return ResponseEntity.ok(universityService.getUniversitys());
	}

	@PostMapping
	public ResponseEntity<UniversityDTO> createUniversity(
			@Validated(ValidationGroups.Create.class) @RequestBody UniversityDTO universityDTO) {
		UniversityDTO createdUniversity =
				universityService.createUniversity(universityDTO);

		URI location =
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{universityId}")
						.buildAndExpand(createdUniversity.getId()).toUri();

		return ResponseEntity.created(location).body(createdUniversity);
	}

	@GetMapping("/{universityId}")
	public ResponseEntity<UniversityDTO> getUniversity(
			@PathVariable Long universityId) {
		return ResponseEntity.ok(universityService.getUniversity(universityId));
	}

	@PutMapping("/{universityId}")
	public ResponseEntity<UniversityDTO> updateUniversity(
			@PathVariable Long universityId,
			@Validated(ValidationGroups.Update.class) @RequestBody UniversityDTO universityDTO) {
		return ResponseEntity
				.ok(universityService.updateUniversity(universityId, universityDTO));
	}

	@DeleteMapping("/{universityId}")
	public ResponseEntity<Void> deleteUniversity(
			@PathVariable Long universityId) {
		universityService.deleteUniversity(universityId);

		return ResponseEntity.noContent().build();
	}
}
