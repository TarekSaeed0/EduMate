package com.github.hciteam.edumate.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.RefreshRequest;
import com.github.hciteam.edumate.model.Role;
import com.github.hciteam.edumate.model.SigninRequest;
import com.github.hciteam.edumate.model.AuthenticationResponse;
import com.github.hciteam.edumate.model.SignupRequest;
import com.github.hciteam.edumate.repository.UserRepository;

@Service
public class AuthenticationService {
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(JwtService jwtService,
			UserRepository userRepository,
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User signup(SignupRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email is already in use");
		}

		User user =
				User.builder().password(passwordEncoder.encode(request.getPassword()))
						.role(Role.STUDENT).build();

		Student student = Student.builder().id(request.getStudentId())
				.name(request.getName()).gender(request.getGender())
				.email(request.getUniversityEmail()).build();

		user.setStudent(student);
		student.setUser(user);

		return userRepository.save(user);
	}

	public AuthenticationResponse signin(SigninRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);

		return new AuthenticationResponse(accessToken, refreshToken);
	}

	public AuthenticationResponse refreshToken(RefreshRequest request) {
		String refreshToken = request.getRefreshToken();

		if (jwtService.isTokenExpired(refreshToken)) {
			throw new IllegalArgumentException("Refresh token is expired");
		}

		String username = jwtService.extractUsername(refreshToken);
		User user = userRepository.findByEmail(username).orElseThrow();

		String accessToken = jwtService.generateAccessToken(user);
		String newRefreshToken = jwtService.generateRefreshToken(user);

		return new AuthenticationResponse(accessToken, newRefreshToken);
	}
}
