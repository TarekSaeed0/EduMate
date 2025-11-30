package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.dto.SigninRequest;
import com.github.hciteam.edumate.dto.SignupRequest;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserDTO> signup(
			@Valid @RequestBody SignupRequest request) {
		return ResponseEntity.ok(authenticationService.signup(request));
	}

	@PostMapping("/signin")
	public ResponseEntity<Void> signin(
			@Valid @RequestBody SigninRequest signinRequest,
			HttpServletRequest request, HttpServletResponse response) {
		authenticationService.signin(signinRequest, request, response);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/me")
	public ResponseEntity<UserDTO> me(Authentication authentication) {
		return ResponseEntity.ok(authenticationService.me(authentication));
	}
}
