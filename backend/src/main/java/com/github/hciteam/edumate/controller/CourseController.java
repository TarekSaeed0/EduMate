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
import com.github.hciteam.edumate.dto.CourseDTO;
import com.github.hciteam.edumate.service.CourseService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public ResponseEntity<List<CourseDTO>> getCourses(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String name) {
		return ResponseEntity.ok(courseService.getCourses(code, name));
	}

	@PostMapping
	public ResponseEntity<CourseDTO> createCourse(
			@Validated(ValidationGroups.Create.class) @RequestBody CourseDTO courseDTO) {
		CourseDTO createdCourse = courseService.createCourse(courseDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{courseId}").buildAndExpand(createdCourse.getId()).toUri();

		return ResponseEntity.created(location).body(createdCourse);
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) {
		return ResponseEntity.ok(courseService.getCourse(courseId));
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId,
			@Validated(ValidationGroups.Update.class) @RequestBody CourseDTO courseDTO) {
		return ResponseEntity.ok(courseService.updateCourse(courseId, courseDTO));
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
		courseService.deleteCourse(courseId);

		return ResponseEntity.noContent().build();
	}
}
