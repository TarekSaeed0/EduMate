package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamGroup;
import com.github.hciteam.edumate.repository.TeamGroupRepository;
import com.github.hciteam.edumate.dto.TeamDTO;
import com.github.hciteam.edumate.dto.TeamGroupDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;

@Mapper(componentModel = "spring",
		uses = {TeamGroupMapper.class, StudentMapper.class})
public abstract class TeamMapper {
	@Autowired
	protected TeamGroupRepository groupRepository;

	public abstract TeamDTO toDTO(Team team);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "group", target = "group", qualifiedByName = "mapGroup")
	@Mapping(target = "invites", ignore = true)
	@Mapping(target = "requests", ignore = true)
	@Mapping(target = "members", ignore = true)
	public abstract Team toEntity(TeamDTO teamDTO);

	@Named("mapGroup")
	protected TeamGroup mapGroup(TeamGroupDTO groupDTO) {
		return groupRepository.findById(groupDTO.getId())
				.orElseThrow(() -> new CourseOfferingNotFoundException());
	}
}
