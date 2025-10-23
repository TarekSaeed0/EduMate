package com.github.hciteam.edumate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class GreetingController {
	private final StudentRepository studentRepository;

	public GreetingController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping("/greeting")
	public ResponseEntity<String> greet(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUser(user).orElseThrow();
		return ResponseEntity
				.ok("Hello, " + student.getName().trim().split("\\s+")[0] + "!");
	}

}
