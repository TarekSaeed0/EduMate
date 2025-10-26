package com.github.hciteam.edumate.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import com.github.hciteam.edumate.entity.Role;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO toDTO(User user);

	default Set<String> mapRolesToStrings(Set<Role> roles) {
		return roles.stream().map(Role::getName).collect(Collectors.toSet());
	}
}
