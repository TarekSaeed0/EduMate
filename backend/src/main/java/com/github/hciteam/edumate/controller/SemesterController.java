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
import com.github.hciteam.edumate.model.SemesterDTO;
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
				.path("/{id}").buildAndExpand(createdSemester.getId()).toUri();

		return ResponseEntity.created(location).body(createdSemester);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SemesterDTO> getSemester(@PathVariable Long id) {
		return ResponseEntity.ok(semesterService.getSemester(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SemesterDTO> updateSemester(@PathVariable Long id,
			@RequestBody SemesterDTO semesterDTO) {
		return ResponseEntity.ok(semesterService.updateSemester(id, semesterDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
		semesterService.deleteSemester(id);

		return ResponseEntity.noContent().build();
	}

}
