package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.SigninRequest;
import com.github.hciteam.edumate.model.SigninResponse;
import com.github.hciteam.edumate.model.SignupRequest;
import com.github.hciteam.edumate.service.AuthenticationService;
import com.github.hciteam.edumate.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	private final JwtService jwtService;
	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService,
			AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signup(
			@Valid @RequestBody SignupRequest request) {
		User user = authenticationService.signup(request);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/signin")
	public ResponseEntity<SigninResponse> signin(
			@Valid @RequestBody SigninRequest request) {
		User user = authenticationService.signin(request);
		String token = jwtService.generateToken(user);
		SigninResponse response = new SigninResponse(token);
		return ResponseEntity.ok(response);
	}
}
