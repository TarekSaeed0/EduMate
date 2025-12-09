package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import com.github.hciteam.edumate.model.TeamJoinRequest;
import com.github.hciteam.edumate.dto.TeamJoinRequestDTO;

@Mapper(componentModel = "spring",
		uses = {TeamMapper.class, StudentMapper.class})
public interface TeamJoinRequestMapper {
	TeamJoinRequestDTO toDTO(TeamJoinRequest request);

	TeamJoinRequest toEntity(TeamJoinRequestDTO requestDTO);
}
