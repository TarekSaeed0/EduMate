package com.github.hciteam.edumate.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.UserDTO;

@Mapper(componentModel = "spring",
		uses = {UserRoleMapper.class, StudentMapper.class})
public abstract class UserMapper {
	@Mapping(target = "password", ignore = true)
	public abstract UserDTO toDTO(User user);

	@Mapping(target = "id", ignore = true)
	public abstract User toEntity(UserDTO userDTO);

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
	protected void setStudentIdIfNew(UserDTO userDTO, @MappingTarget User user) {
		if (userDTO.getStudent() != null && user.getStudent().getId() == null) {
			user.getStudent().setId(userDTO.getStudent().getId());
		}
	}
}
