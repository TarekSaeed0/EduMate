package com.github.hciteam.edumate.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.Role;
import com.github.hciteam.edumate.model.SigninRequest;
import com.github.hciteam.edumate.model.SignupRequest;
import com.github.hciteam.edumate.model.UserDTO;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final UserMapper userMapper;
	private SecurityContextRepository securityContextRepository =
			new HttpSessionSecurityContextRepository();
	private final SecurityContextHolderStrategy securityContextHolderStrategy =
			SecurityContextHolder.getContextHolderStrategy();

	public AuthenticationService(AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, UserRepository userRepository,
			StudentRepository studentRepository, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
		this.userMapper = userMapper;
	}

	public UserDTO signup(SignupRequest signupRequest) {
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new UserAlreadyExistsException();
		}

		User user = User.builder().email(signupRequest.getEmail())
				.password(passwordEncoder.encode(signupRequest.getPassword()))
				.role(Role.STUDENT).build();

		if (studentRepository.existsById(signupRequest.getStudentId())) {
			throw new StudentAlreadyExistsException();
		}

		Student student = Student.builder().id(signupRequest.getStudentId())
				.name(signupRequest.getName()).gender(signupRequest.getGender())
				.email(signupRequest.getUniversityEmail()).build();

		user.setStudent(student);
		student.setUser(user);

		return userMapper.toDTO(userRepository.save(user));
	}

	public void signin(SigninRequest signinRequest, HttpServletRequest request,
			HttpServletResponse response) {
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
						signinRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);

		SecurityContext context =
				securityContextHolderStrategy.createEmptyContext();
		context.setAuthentication(authentication);
		securityContextHolderStrategy.setContext(context);
		securityContextRepository.saveContext(context, request, response);
	}

	public UserDTO me(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return userMapper.toDTO(user);
	}
}
