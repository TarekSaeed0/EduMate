package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.exception.UserRoleNotFoundException;
import com.github.hciteam.edumate.model.UserRole;
import com.github.hciteam.edumate.repository.UserRoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserRoleMapper {
	@Autowired
	protected UserRoleRepository roleRepository;

	String toString(UserRole role) {
		return role.getName();
	}

	UserRole toEntity(String roleName) {
		return roleRepository.findByName(roleName)
				.orElseThrow(() -> new UserRoleNotFoundException(roleName));
	}
}
