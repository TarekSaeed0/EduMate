package com.github.hciteam.edumate.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.UserRole;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.UserCreationDTO;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.repository.UserRoleRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.exception.UserNotFoundException;
import com.github.hciteam.edumate.exception.UserRoleNotFoundException;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserRoleRepository roleRepository;
	private final StudentRepository studentRepository;
	private final UserMapper userMapper;

	public UserService(PasswordEncoder passwordEncoder,
			UserRepository userRepository, UserRoleRepository roleRepository,
			StudentRepository studentRepository, UserMapper userMapper) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.studentRepository = studentRepository;
		this.userMapper = userMapper;
	}

	public UserDTO me(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return userMapper.toDTO(user);
	}

	public List<UserDTO> getUsers() {
		return userRepository.findAll().stream().map(userMapper::toDTO).toList();
	}

	public UserDTO createUser(UserCreationDTO userDTO) {
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException();
		}

		Set<UserRole> roles = new HashSet<>();
		if (userDTO.getRoles() != null) {
			for (String roleName : userDTO.getRoles()) {
				UserRole role = roleRepository.findByName(roleName)
						.orElseThrow(() -> new UserRoleNotFoundException());
				roles.add(role);
			}
		}

		User user = User.builder().email(userDTO.getEmail())
				.password(passwordEncoder.encode(userDTO.getPassword())).roles(roles)
				.build();

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

	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		return null;
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
