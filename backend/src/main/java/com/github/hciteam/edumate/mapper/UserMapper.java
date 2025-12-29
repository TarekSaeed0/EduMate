package com.github.hciteam.edumate.mapper;

import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Role;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.repository.RoleRepository;
import com.github.hciteam.edumate.dto.SignupRequest;
import com.github.hciteam.edumate.dto.UserDTO;
import com.github.hciteam.edumate.exception.RoleNotFoundException;

@Mapper(componentModel = "spring",
		uses = {RoleMapper.class, StudentMapper.class})
public abstract class UserMapper {
	@Autowired
	protected RoleRepository roleRepository;

	@Mapping(target = "password", ignore = true)
	public abstract UserDTO toDTO(User user);

	@Mapping(target = "id", ignore = true)
	public abstract User toEntity(UserDTO userDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "student.name", source = "name")
	public abstract User toEntity(SignupRequest signupRequest);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	public abstract void updateEntityFromDTO(UserDTO userDTO,
			@MappingTarget User user);

	@AfterMapping
	protected void linkStudentToUser(@MappingTarget User user) {
		if (user.getStudent() != null) {
			user.getStudent().setUser(user);
		}
	}

	@AfterMapping
	protected void setUserSignupRoles(SignupRequest signupRequest,
			@MappingTarget User user) {
		Role studentRole = roleRepository.findByName("STUDENT")
				.orElseThrow(() -> new RoleNotFoundException("STUDENT"));

		user.setRoles(Set.of(studentRole));
	}
}
