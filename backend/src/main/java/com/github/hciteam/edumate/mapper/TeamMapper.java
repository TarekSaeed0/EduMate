package com.github.hciteam.edumate.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamStatus;
import com.github.hciteam.edumate.repository.TeamRepository;
import com.github.hciteam.edumate.dto.TeamDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;

@Mapper(componentModel = "spring",
		uses = {TeamGroupMapper.class, StudentMapper.class})
public abstract class TeamMapper {
	@Autowired
	protected TeamRepository teamRepository;

	@Mapping(target = "status", ignore = true)
	public abstract TeamDTO toDTO(Team team);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "group", target = "group", qualifiedByName = "mapGroup")
	@Mapping(source = "leader", target = "leader", qualifiedByName = "mapStudent")
	@Mapping(target = "invites", ignore = true)
	@Mapping(target = "requests", ignore = true)
	@Mapping(target = "members", ignore = true)
	public abstract Team toEntity(TeamDTO teamDTO);

	@AfterMapping
	protected void setStatus(Team team, @MappingTarget TeamDTO teamDTO) {
		if (team.getMembers().size() >= team.getGroup().getMaximumMemberCount()) {
			teamDTO.setStatus(TeamStatus.COMPLETE);
		} else if (team.getMembers().size() >= team.getGroup()
				.getMinimumMemberCount()) {
			teamDTO.setStatus(TeamStatus.SUFFICIENT);
		} else {
			teamDTO.setStatus(TeamStatus.INCOMPLETE);
		}
	}

	@Named("mapTeam")
	protected Team mapTeam(TeamDTO teamDTO) {
		return teamRepository.findById(teamDTO.getId())
				.orElseThrow(() -> new CourseOfferingNotFoundException());
	}
}
