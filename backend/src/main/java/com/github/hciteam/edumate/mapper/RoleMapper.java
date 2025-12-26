package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.exception.RoleNotFoundException;
import com.github.hciteam.edumate.model.Role;
import com.github.hciteam.edumate.repository.RoleRepository;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
	@Autowired
	protected RoleRepository roleRepository;

	String toString(Role role) {
		return role.getName();
	}

	Role toEntity(String roleName) {
		return roleRepository.findByName(roleName)
				.orElseThrow(() -> new RoleNotFoundException(roleName));
	}
}
