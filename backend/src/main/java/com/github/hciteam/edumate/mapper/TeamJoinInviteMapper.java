package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;

@Mapper(componentModel = "spring",
		uses = {TeamMapper.class, StudentMapper.class})
public interface TeamJoinInviteMapper {
	TeamJoinInviteDTO toDTO(TeamJoinInvite invite);

	TeamJoinInvite toEntity(TeamJoinInviteDTO inviteDTO);
}
