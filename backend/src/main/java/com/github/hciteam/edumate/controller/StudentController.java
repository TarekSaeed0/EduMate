package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/student")
public class StudentController {
	StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentDTO> me(Authentication authentication) {
		return ResponseEntity.ok(studentService.me(authentication));
	}
}
