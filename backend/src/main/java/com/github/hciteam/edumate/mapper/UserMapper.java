package com.github.hciteam.edumate.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.github.hciteam.edumate.model.UserRole;
import com.github.hciteam.edumate.repository.UserRoleRepository;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.dto.UserRequestDTO;
import com.github.hciteam.edumate.exception.UserRoleNotFoundException;

@Mapper(componentModel = "spring",
		uses = {UserRoleRepository.class, StudentMapper.class})
public abstract class UserMapper {
	private final PasswordEncoder passwordEncoder;
	protected final UserRoleRepository roleRepository;

	protected UserMapper(PasswordEncoder passwordEncoder,
			UserRoleRepository roleRepository) {
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	public abstract UserDTO toDTO(User user);

	@Mapping(target = "id", ignore = true)
	public abstract User toEntity(UserRequestDTO userDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	public abstract void updateUserFromDTO(UserRequestDTO userDTO,
			@MappingTarget User user);

	String encodePassword(String password) {
		if (password == null) {
			return null;
		}

		return passwordEncoder.encode(password);
	}

	Set<String> mapRolesToStrings(Set<UserRole> roles) {
		return roles.stream().map(UserRole::getName).collect(Collectors.toSet());
	}

	Set<UserRole> mapStringsToRoles(Set<String> roleNames) {
		if (roleNames == null) {
			return Set.of();
		}

		return roleNames.stream()
				.map(roleName -> roleRepository.findByName(roleName)
						.orElseThrow(() -> new UserRoleNotFoundException()))
				.collect(Collectors.toSet());
	}
}
