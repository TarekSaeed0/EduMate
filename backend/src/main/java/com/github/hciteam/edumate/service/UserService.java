package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.UniversityRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.exception.UserNotFoundException;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;
import com.github.hciteam.edumate.exception.UniversityNotFoundException;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final UniversityRepository universityRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository,
			StudentRepository studentRepository,
			UniversityRepository universityRepository, UserMapper userMapper,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
		this.universityRepository = universityRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserDTO> getUsers() {
		return userRepository.findAll().stream().map(userMapper::toDTO).toList();
	}

	public UserDTO createUser(UserDTO userDTO) {
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException(userDTO.getEmail());
		}

		User user = userMapper.toEntity(userDTO);

		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		if (user.getStudent() != null) {
			user.getStudent().setUniversity(universityRepository.findAll().stream()
					.findFirst().orElseThrow(() -> new UniversityNotFoundException()));
		}

		return userMapper.toDTO(userRepository.save(user));
	}

	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		return userRepository.findById(userId).map(existingUser -> {
			userMapper.updateEntityFromDTO(userDTO, existingUser);

			if (userDTO.getPassword() != null) {
				existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			}

			return userMapper.toDTO(userRepository.save(existingUser));
		}).orElseThrow(() -> new UserNotFoundException());
	}

	public UserDTO getUser(Long userId) {
		return userRepository.findById(userId).map(userMapper::toDTO)
				.orElseThrow(() -> new UserNotFoundException(userId));
	}

	public void deleteUser(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException(userId);
		}

		userRepository.deleteById(userId);
	}
}
