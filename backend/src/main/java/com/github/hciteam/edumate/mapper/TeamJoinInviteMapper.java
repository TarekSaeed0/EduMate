package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;

@Mapper(componentModel = "spring",
		uses = {TeamMapper.class, StudentMapper.class})
public interface TeamJoinInviteMapper {
	TeamJoinInviteDTO toDTO(TeamJoinInvite invite);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "team", target = "team", qualifiedByName = "mapTeam")
	@Mapping(source = "student", target = "student",
			qualifiedByName = "mapStudent")
	@Mapping(target = "status", ignore = true)
	TeamJoinInvite toEntity(TeamJoinInviteDTO inviteDTO);
}
