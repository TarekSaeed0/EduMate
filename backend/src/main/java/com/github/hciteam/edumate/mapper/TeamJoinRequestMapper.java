package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.TeamJoinRequest;
import com.github.hciteam.edumate.dto.TeamJoinRequestDTO;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface TeamJoinRequestMapper {
	@Mapping(source = "team.id", target = "teamId")
	TeamJoinRequestDTO toDTO(TeamJoinRequest request);

	@Mapping(source = "teamId", target = "team.id")
	TeamJoinRequest toEntity(TeamJoinRequestDTO requestDTO);
}
