package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.model.StudentCourseDTO;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.model.StudentTaskDTO;
import com.github.hciteam.edumate.service.StudentService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/{id}")
	@PreAuthorize("@authorizationService.isStudentSelf(#id) or hasRole('ADMIN')")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
		return ResponseEntity.ok(studentService.getStudent(id));
	}

	@GetMapping("/{id}/tasks")
	@PreAuthorize("@authorizationService.isStudentSelf(#id) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentTaskDTO>> getStudentTasks(
			@PathVariable Long id) {
		return ResponseEntity.ok(studentService.getStudentTasks(id));
	}

	@GetMapping("/{id}/courses")
	@PreAuthorize("@authorizationService.isStudentSelf(#id) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentCourseDTO>> getStudentCourses(
			@PathVariable Long id) {
		return ResponseEntity.ok(studentService.getStudentCourses(id));
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentDTO> getCurrentStudent(
			Authentication authentication) {
		return ResponseEntity.ok(studentService.getCurrentStudent(authentication));
	}

	@GetMapping("/me/tasks")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<List<StudentTaskDTO>> getCurrentStudentTasks(
			Authentication authentication) {
		return ResponseEntity
				.ok(studentService.getCurrentStudentTasks(authentication));
	}

	@GetMapping("/me/courses")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<List<StudentCourseDTO>> getCurrentStudentCourses(
			Authentication authentication) {
		return ResponseEntity
				.ok(studentService.getCurrentStudentCourses(authentication));
	}
}
