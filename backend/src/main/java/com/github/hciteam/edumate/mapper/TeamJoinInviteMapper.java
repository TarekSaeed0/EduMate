package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;

@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface TeamJoinInviteMapper {
	@Mapping(source = "student.id", target = "studentId")
	TeamJoinInviteDTO toDTO(TeamJoinInvite invite);

	@Mapping(source = "studentId", target = "student.id")
	TeamJoinInvite toEntity(TeamJoinInviteDTO inviteDTO);
}
