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
import com.github.hciteam.edumate.entity.Role;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.StudentCourse;
import com.github.hciteam.edumate.entity.StudentTask;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.SigninRequest;
import com.github.hciteam.edumate.model.SignupRequest;
import com.github.hciteam.edumate.model.StudentCourseStatus;
import com.github.hciteam.edumate.model.UserDTO;
import com.github.hciteam.edumate.repository.RoleRepository;
import com.github.hciteam.edumate.repository.SemesterCourseRepository;
import com.github.hciteam.edumate.repository.StudentCourseRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.key.StudentCourseKey;
import com.github.hciteam.edumate.key.StudentTaskKey;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final StudentRepository studentRepository;
	private final SemesterCourseRepository semesterCourseRepository;
	private final StudentCourseRepository studentCourseRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final UserMapper userMapper;
	private SecurityContextRepository securityContextRepository =
			new HttpSessionSecurityContextRepository();
	private final SecurityContextHolderStrategy securityContextHolderStrategy =
			SecurityContextHolder.getContextHolderStrategy();

	public AuthenticationService(AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, UserRepository userRepository,
			RoleRepository roleRepository, StudentRepository studentRepository,
			SemesterCourseRepository semesterCourseRepository,
			StudentCourseRepository studentCourseRepository,
			StudentTaskRepository studentTaskRepository, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.studentRepository = studentRepository;
		this.semesterCourseRepository = semesterCourseRepository;
		this.studentCourseRepository = studentCourseRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.userMapper = userMapper;
	}

	@Transactional
	public UserDTO signup(SignupRequest signupRequest) {
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new UserAlreadyExistsException();
		}

		Role studentRole = roleRepository.findByName("STUDENT")
				.orElseThrow(() -> new RuntimeException("STUDENT Role not found"));

		Set<Role> roles = new HashSet<>();
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

		List<StudentCourse> studentCourses = semesterCourseRepository.findAll()
				.stream()
				.map(semesterCourse -> new StudentCourse(
						new StudentCourseKey(createdUser.getStudent().getId(), null),
						student, semesterCourse, StudentCourseStatus.REGISTERED))
				.toList();

		studentCourseRepository.saveAll(studentCourses);

		List<StudentTask> studentTasks = semesterCourseRepository.findAll().stream()
				.flatMap(semesterCourse -> semesterCourse.getTasks().stream()
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
