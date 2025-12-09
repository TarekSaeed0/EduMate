package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.TeamGroup;
import com.github.hciteam.edumate.dto.TeamGroupDTO;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public interface TeamGroupMapper {
	TeamGroupDTO toDTO(TeamGroup group);

	@Mapping(target = "teams", ignore = true)
	TeamGroup toEntity(TeamGroupDTO groupDTO);
}
