package com.github.hciteam.edumate.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.SigninRequest;
import com.github.hciteam.edumate.model.SignupRequest;
import com.github.hciteam.edumate.repository.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository,
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User signup(SignupRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setUniversityEmail(request.getUniversityEmail());
		user.setStudentId(request.getStudentId());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		return userRepository.save(user);
	}

	public User signin(SigninRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getEmail(), request.getPassword()));
		return userRepository.findByEmail(request.getEmail()).orElseThrow();
	}
}
