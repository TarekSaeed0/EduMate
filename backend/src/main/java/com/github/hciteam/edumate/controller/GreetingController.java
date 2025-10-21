package com.github.hciteam.edumate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.entity.User;

@RestController
@RequestMapping("/api")
public class GreetingController {

	@GetMapping("/greeting")
	public ResponseEntity<String> greet(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return ResponseEntity
				.ok("Hello, " + user.getName().trim().split("\\s+")[0] + "!");
	}

}
