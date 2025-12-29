package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;

@Mapper(componentModel = "spring",
		uses = {CourseMapper.class, SemesterMapper.class})
public abstract class CourseOfferingMapper {
	@Autowired
	protected CourseOfferingRepository offeringRepository;

	public abstract CourseOfferingDTO toDTO(CourseOffering offering);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "course", target = "course", qualifiedByName = "mapCourse")
	@Mapping(source = "semester", target = "semester",
			qualifiedByName = "mapSemester")
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	@Mapping(target = "teamGroups", ignore = true)
	public abstract CourseOffering toEntity(CourseOfferingDTO offeringDTO);

	@Named("mapOffering")
	protected CourseOffering mapOffering(CourseOfferingDTO offeringDTO) {
		return offeringRepository.findById(offeringDTO.getId()).orElseThrow(
				() -> new CourseOfferingNotFoundException(offeringDTO.getId()));
	}
}
