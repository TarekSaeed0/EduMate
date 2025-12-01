package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.dto.TeamDTO;

@Mapper(componentModel = "spring",
		uses = {TeamGroupMapper.class, StudentMapper.class})
public interface TeamMapper {
	TeamDTO toDTO(Team team);

	@Mapping(target = "invites", ignore = true)
	@Mapping(target = "requests", ignore = true)
	Team toEntity(TeamDTO teamDTO);
}
