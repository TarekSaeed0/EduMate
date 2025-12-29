package com.github.hciteam.edumate.service;

import java.util.List;
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
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.model.University;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.model.UserPrincipal;
import com.github.hciteam.edumate.dto.SigninRequest;
import com.github.hciteam.edumate.dto.SignupRequest;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.repository.UniversityRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import com.github.hciteam.edumate.exception.UniversityNotFoundException;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.mapper.UserMapper;

@Service
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UniversityRepository universityRepository;
	private final CourseOfferingRepository offeringRepository;
	private final CourseRegistrationRepository registrationRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final UserMapper userMapper;
	private SecurityContextRepository securityContextRepository =
			new HttpSessionSecurityContextRepository();
	private final SecurityContextHolderStrategy securityContextHolderStrategy =
			SecurityContextHolder.getContextHolderStrategy();

	public AuthenticationService(AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, UserRepository userRepository,
			UniversityRepository universityRepository,
			CourseOfferingRepository courseOfferingRepository,
			CourseRegistrationRepository registrationRepository,
			StudentTaskRepository studentTaskRepository, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.universityRepository = universityRepository;
		this.offeringRepository = courseOfferingRepository;
		this.registrationRepository = registrationRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.userMapper = userMapper;
	}

	@Transactional
	public UserDTO signup(SignupRequest signupRequest) {
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new UserAlreadyExistsException(signupRequest.getEmail());
		}

		User user = userMapper.toEntity(signupRequest);

		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

		University university = universityRepository
				.findByName("Faculty of Engineering, Alexandria University")
				.orElseThrow(() -> new UniversityNotFoundException(
						"Faculty of Engineering, Alexandria University"));

		user.getStudent().setUniversity(university);

		User persistedUser = userRepository.save(user);

		List<CourseRegistration> registrations = offeringRepository.findAll()
				.stream().map(offering -> new CourseRegistration(offering,
						persistedUser.getStudent()))
				.toList();

		registrationRepository.saveAll(registrations);

		List<StudentTask> studentTasks = offeringRepository.findAll().stream()
				.flatMap(offering -> offering.getTasks().stream()
						.map(task -> new StudentTask(persistedUser.getStudent(), task)))
				.toList();

		studentTaskRepository.saveAll(studentTasks);

		return userMapper.toDTO(persistedUser);
	}

	public UserDTO signin(SigninRequest signinRequest, HttpServletRequest request,
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

		return userMapper.toDTO(
				userMapper.toEntity((UserPrincipal) authentication.getPrincipal()));
	}

	public UserDTO me(Authentication authentication) {
		return userMapper.toDTO(
				userMapper.toEntity((UserPrincipal) authentication.getPrincipal()));
	}
}
