package com.github.hciteam.edumate.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import com.github.hciteam.edumate.model.UserRole;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO toDTO(User user);

	default Set<String> mapRolesToStrings(Set<UserRole> roles) {
		return roles.stream().map(UserRole::getName).collect(Collectors.toSet());
	}
}
