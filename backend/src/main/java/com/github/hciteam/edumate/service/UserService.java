package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.dto.UserRequestDTO;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.UserRepository;
import com.github.hciteam.edumate.exception.UserAlreadyExistsException;
import com.github.hciteam.edumate.exception.UserNotFoundException;
import com.github.hciteam.edumate.mapper.UserMapper;
import com.github.hciteam.edumate.exception.StudentAlreadyExistsException;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final UserMapper userMapper;

	public UserService(UserRepository userRepository,
			StudentRepository studentRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
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

	public UserDTO createUser(UserRequestDTO userDTO) {
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException(userDTO.getEmail());
		}

		User user = userMapper.toEntity(userDTO);

		if (user.getStudent() != null) {
			if (studentRepository.existsById(user.getStudent().getId())) {
				throw new StudentAlreadyExistsException();
			}

			user.getStudent().setUser(user);
		}

		return userMapper.toDTO(userRepository.save(user));
	}

	public UserDTO updateUser(Long userId, UserRequestDTO userDTO) {
		return userRepository.findById(userId).map(existingUser -> {
			userMapper.updateUserFromDTO(userDTO, existingUser);
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
