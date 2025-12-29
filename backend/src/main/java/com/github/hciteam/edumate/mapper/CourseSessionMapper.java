package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.CourseSession;
import com.github.hciteam.edumate.dto.CourseSessionDTO;

@Mapper(componentModel = "spring",
		uses = {CourseOfferingMapper.class, TimeSlotMapper.class})
public interface CourseSessionMapper {
	CourseSessionDTO toDTO(CourseSession session);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "slot", target = "slot", qualifiedByName = "mapSlot")
	CourseSession toEntity(CourseSessionDTO sessionDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "slot", target = "slot", qualifiedByName = "mapSlot")
	void updateEntityFromDTO(CourseSessionDTO sessionDTO,
			@MappingTarget CourseSession session);
}
