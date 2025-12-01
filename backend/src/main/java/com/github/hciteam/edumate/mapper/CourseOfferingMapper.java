package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface CourseOfferingMapper {
	@Mapping(source = "semester.id", target = "semesterId")
	CourseOfferingDTO toDTO(CourseOffering offering);

	@Mapping(source = "semesterId", target = "semester.id")
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	@Mapping(target = "teamGroups", ignore = true)
	CourseOffering toEntity(CourseOfferingDTO offeringDTO);
}
