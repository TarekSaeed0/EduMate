package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.TeamJoinRequest;
import com.github.hciteam.edumate.dto.TeamJoinRequestDTO;

@Mapper(componentModel = "spring",
		uses = {TeamMapper.class, StudentMapper.class})
public interface TeamJoinRequestMapper {
	TeamJoinRequestDTO toDTO(TeamJoinRequest request);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "team", target = "team", qualifiedByName = "mapTeam")
	@Mapping(source = "student", target = "student",
			qualifiedByName = "mapStudent")
	@Mapping(target = "status", ignore = true)
	TeamJoinRequest toEntity(TeamJoinRequestDTO requestDTO);
}
