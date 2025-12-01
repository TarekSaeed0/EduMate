package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.UserCreationDTO;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.service.UserService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public ResponseEntity<UserDTO> me(Authentication authentication) {
		return ResponseEntity.ok(userService.me(authentication));
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(
			@RequestBody UserCreationDTO userDTO) {
		UserDTO createdUser = userService.createUser(userDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{userId}").buildAndExpand(createdUser.getId()).toUri();

		return ResponseEntity.created(location).body(createdUser);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,
			@RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(userService.updateUser(userId, userDTO));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);

		return ResponseEntity.noContent().build();
	}
}
