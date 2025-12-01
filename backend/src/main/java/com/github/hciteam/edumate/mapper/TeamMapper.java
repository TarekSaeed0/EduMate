package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.dto.TeamDTO;

@Mapper(componentModel = "spring",
		uses = {TeamGroupMapper.class, StudentMapper.class})
public interface TeamMapper {
	TeamDTO toDTO(Team team);

	Team toEntity(TeamDTO teamDTO);
}
