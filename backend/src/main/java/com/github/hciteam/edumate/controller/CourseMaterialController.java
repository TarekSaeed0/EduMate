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
import com.github.hciteam.edumate.dto.CourseMaterialDTO;
import com.github.hciteam.edumate.service.CourseMaterialService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/course-materials")
public class CourseMaterialController {
	private final CourseMaterialService materialService;

	public CourseMaterialController(CourseMaterialService materialService) {
		this.materialService = materialService;
	}

	@GetMapping
	public ResponseEntity<List<CourseMaterialDTO>> getMaterials(
			@RequestParam(required = false) Long courseId) {
		return ResponseEntity.ok(materialService.getMaterials(courseId));
	}

	@PostMapping
	public ResponseEntity<CourseMaterialDTO> createMaterial(
			@Validated(ValidationGroups.Create.class) @RequestBody CourseMaterialDTO materialDTO) {
		CourseMaterialDTO createdMaterial =
				materialService.createMaterial(materialDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{materialId}").buildAndExpand(createdMaterial.getId()).toUri();

		return ResponseEntity.created(location).body(createdMaterial);
	}

	@GetMapping("/{materialId}")
	public ResponseEntity<CourseMaterialDTO> getMaterial(
			@PathVariable Long materialId) {
		return ResponseEntity.ok(materialService.getMaterial(materialId));
	}

	@PutMapping("/{materialId}")
	public ResponseEntity<CourseMaterialDTO> updateMaterial(
			@PathVariable Long materialId,
			@Validated(ValidationGroups.Update.class) @RequestBody CourseMaterialDTO materialDTO) {
		return ResponseEntity
				.ok(materialService.updateMaterial(materialId, materialDTO));
	}

	@DeleteMapping("/{materialId}")
	public ResponseEntity<Void> deleteMaterial(@PathVariable Long materialId) {
		materialService.deleteMaterial(materialId);

		return ResponseEntity.noContent().build();
	}
}
