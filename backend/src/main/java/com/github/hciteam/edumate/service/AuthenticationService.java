package com.github.hciteam.edumate.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.github.hciteam.edumate.model.UserRole;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.SigninRequest;
import com.github.hciteam.edumate.dto.SignupRequest;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.repository.UserRoleRepository;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.exception.UserRoleNotFoundException;
import com.github.hciteam.edumate.key.StudentTaskKey;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserRoleRepository roleRepository;
	private final StudentRepository studentRepository;
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
			UserRoleRepository roleRepository, StudentRepository studentRepository,
			CourseOfferingRepository courseOfferingRepository,
			CourseRegistrationRepository registrationRepository,
			StudentTaskRepository studentTaskRepository, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.studentRepository = studentRepository;
		this.offeringRepository = courseOfferingRepository;
		this.registrationRepository = registrationRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.userMapper = userMapper;
	}

	@Transactional
	public UserDTO signup(SignupRequest signupRequest) {
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new UserAlreadyExistsException();
		}

		UserRole studentRole = roleRepository.findByName("STUDENT")
				.orElseThrow(() -> new UserRoleNotFoundException());

		Set<UserRole> roles = new HashSet<>();
		roles.add(studentRole);

		User user = User.builder().email(signupRequest.getEmail())
				.password(passwordEncoder.encode(signupRequest.getPassword()))
				.roles(roles).build();

		if (studentRepository.existsById(signupRequest.getStudentId())) {
			throw new StudentAlreadyExistsException();
		}

		Student student = Student.builder().id(signupRequest.getStudentId())
				.name(signupRequest.getName()).gender(signupRequest.getGender())
				.email(signupRequest.getUniversityEmail()).build();

		user.setStudent(student);
		student.setUser(user);

		User createdUser = userRepository.save(user);

		List<CourseRegistration> registrations =
				offeringRepository
						.findAll().stream().map(offering -> new CourseRegistration(null,
								offering, student, CourseRegistrationStatus.REGISTERED))
						.toList();

		registrationRepository.saveAll(registrations);

		List<StudentTask> studentTasks = offeringRepository.findAll().stream()
				.flatMap(offering -> offering.getTasks().stream()
						.map(task -> new StudentTask(
								new StudentTaskKey(createdUser.getStudent().getId(),
										task.getId()),
								student, task, null)))
				.toList();

		studentTaskRepository.saveAll(studentTasks);

		return userMapper.toDTO(createdUser);
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
