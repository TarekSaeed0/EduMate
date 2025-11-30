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
import com.github.hciteam.edumate.exception.UserNotFoundException;
import com.github.hciteam.edumate.key.StudentTaskKey;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class UserService {
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

	public UserService(AuthenticationManager authenticationManager,
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

	public UserDTO me(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return userMapper.toDTO(user);
	}

	public List<UserDTO> getUsers() {
		return userRepository.findAll().stream().map(userMapper::toDTO).toList();
	}

	public UserDTO createUser(UserDTO userDTO) {
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException();
		}

		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode("defaultPassword"));

		Set<UserRole> roles = new HashSet<>();
		if (userDTO.getRoles() != null) {
			for (String roleName : userDTO.getRoles()) {
				UserRole role = roleRepository.findByName(roleName).orElseThrow(
						() -> new RuntimeException("Role not found: " + roleName));
				roles.add(role);
			}
		}
		user.setRoles(roles);

		if (userDTO.getStudent() != null) {
			if (studentRepository.existsById(userDTO.getStudent().getId())) {
				throw new StudentAlreadyExistsException();
			}

			Student student = Student.builder().id(userDTO.getStudent().getId())
					.name(userDTO.getStudent().getName())
					.gender(userDTO.getStudent().getGender())
					.email(userDTO.getStudent().getEmail()).user(user).build();

			user.setStudent(student);
		}

		return userMapper.toDTO(userRepository.save(user));
	}

	public UserDTO getUser(Long userId) {
		return userRepository.findById(userId).map(userMapper::toDTO)
				.orElseThrow(() -> new UserNotFoundException());
	}

	public void deleteUser(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException();
		}

		userRepository.deleteById(userId);
	}
}
