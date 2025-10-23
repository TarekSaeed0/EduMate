package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.SigninRequest;
import com.github.hciteam.edumate.model.AuthenticationResponse;
import com.github.hciteam.edumate.model.RefreshRequest;
import com.github.hciteam.edumate.model.SignupRequest;
import com.github.hciteam.edumate.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<User> signup(
			@Valid @RequestBody SignupRequest request) {
		User user = authenticationService.signup(request);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthenticationResponse> signin(
			@Valid @RequestBody SigninRequest request) {
		AuthenticationResponse response = authenticationService.signin(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthenticationResponse> refreshToken(
			@Valid @RequestBody RefreshRequest request) {
		AuthenticationResponse response =
				authenticationService.refreshToken(request);
		return ResponseEntity.ok(response);
	}
}
