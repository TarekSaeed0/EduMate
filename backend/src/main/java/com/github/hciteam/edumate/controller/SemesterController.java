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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.SemesterDTO;
import com.github.hciteam.edumate.service.SemesterService;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {
	private final SemesterService semesterService;

	public SemesterController(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	@GetMapping
	public ResponseEntity<List<SemesterDTO>> getSemesters() {
		return ResponseEntity.ok(semesterService.getSemesters());
	}

	@PostMapping
	public ResponseEntity<SemesterDTO> createSemester(
			@RequestBody SemesterDTO semesterDTO) {
		SemesterDTO createdSemester = semesterService.createSemester(semesterDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{semesterId}").buildAndExpand(createdSemester.getId()).toUri();

		return ResponseEntity.created(location).body(createdSemester);
	}

	@GetMapping("/{semesterId}")
	public ResponseEntity<SemesterDTO> getSemester(
			@PathVariable Long semesterId) {
		return ResponseEntity.ok(semesterService.getSemester(semesterId));
	}

	@PutMapping("/{semesterId}")
	public ResponseEntity<SemesterDTO> updateSemester(
			@PathVariable Long semesterId, @RequestBody SemesterDTO semesterDTO) {
		return ResponseEntity
				.ok(semesterService.updateSemester(semesterId, semesterDTO));
	}

	@DeleteMapping("/{semesterId}")
	public ResponseEntity<Void> deleteSemester(@PathVariable Long semesterId) {
		semesterService.deleteSemester(semesterId);

		return ResponseEntity.noContent().build();
	}
}
