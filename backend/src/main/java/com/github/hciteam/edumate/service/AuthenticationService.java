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
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.exception.RefreshTokenExpiredException;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class AuthenticationService {
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;

	public AuthenticationService(JwtService jwtService,
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, UserRepository userRepository,
			StudentRepository studentRepository) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
	}

	public User signup(SignupRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserAlreadyExistsException();
		}

		User user = User.builder().email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.STUDENT).build();

		if (studentRepository.existsById(request.getStudentId())) {
			throw new StudentAlreadyExistsException();
		}

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
			throw new RefreshTokenExpiredException();
		}

		String username = jwtService.extractUsername(refreshToken);
		User user = userRepository.findByEmail(username).orElseThrow();

		String accessToken = jwtService.generateAccessToken(user);
		String newRefreshToken = jwtService.generateRefreshToken(user);

		return new AuthenticationResponse(accessToken, newRefreshToken);
	}
}
