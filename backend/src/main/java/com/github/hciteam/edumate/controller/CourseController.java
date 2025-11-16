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
import com.github.hciteam.edumate.model.CourseDTO;
import com.github.hciteam.edumate.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public ResponseEntity<List<CourseDTO>> getCourses() {
		return ResponseEntity.ok(courseService.getCourses());
	}

	@PostMapping
	public ResponseEntity<CourseDTO> createCourse(
			@RequestBody CourseDTO courseDTO) {
		CourseDTO createdCourse = courseService.createCourse(courseDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdCourse.getId()).toUri();

		return ResponseEntity.created(location).body(createdCourse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
		return ResponseEntity.ok(courseService.getCourse(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id,
			@RequestBody CourseDTO courseDTO) {
		return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		courseService.deleteCourse(id);

		return ResponseEntity.noContent().build();
	}

}
