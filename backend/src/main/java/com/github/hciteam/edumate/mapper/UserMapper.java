package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.model.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO toDTO(User user);

	User toEntity(UserDTO userDTO);
}
