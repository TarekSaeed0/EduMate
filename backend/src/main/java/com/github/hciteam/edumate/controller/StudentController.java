package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.model.StudentCourseDTO;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.model.StudentTaskDTO;
import com.github.hciteam.edumate.model.StudentTaskStatus;
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

	@GetMapping("/{studentId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long studentId) {
		return ResponseEntity.ok(studentService.getStudent(studentId));
	}

	@GetMapping("/{studentId}/tasks")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentTaskDTO>> getStudentTasks(
			@PathVariable Long studentId,
			@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) StudentTaskStatus status) {
		return ResponseEntity
				.ok(studentService.getStudentTasks(studentId, courseId, status));
	}

	@GetMapping("/{studentId}/courses")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentCourseDTO>> getStudentCourses(
			@PathVariable Long studentId) {
		return ResponseEntity.ok(studentService.getStudentCourses(studentId));
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
			Authentication authentication,
			@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) StudentTaskStatus status) {
		return ResponseEntity.ok(studentService
				.getCurrentStudentTasks(authentication, courseId, status));
	}

	@GetMapping("/me/courses")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<List<StudentCourseDTO>> getCurrentStudentCourses(
			Authentication authentication) {
		return ResponseEntity
				.ok(studentService.getCurrentStudentCourses(authentication));
	}
}
