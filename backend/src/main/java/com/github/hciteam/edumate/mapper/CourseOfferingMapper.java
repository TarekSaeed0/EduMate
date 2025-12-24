package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.dto.CourseDTO;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.dto.SemesterDTO;
import com.github.hciteam.edumate.exception.CourseNotFoundException;
import com.github.hciteam.edumate.exception.SemesterNotFoundException;

@Mapper(componentModel = "spring",
		uses = {SemesterMapper.class, CourseMapper.class})
public abstract class CourseOfferingMapper {
	@Autowired
	protected SemesterRepository semesterRepository;
	@Autowired
	protected CourseRepository courseRepository;

	public abstract CourseOfferingDTO toDTO(CourseOffering offering);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "semester", target = "semester",
			qualifiedByName = "mapSemester")
	@Mapping(source = "course", target = "course", qualifiedByName = "mapCourse")
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	@Mapping(target = "teamGroups", ignore = true)
	public abstract CourseOffering toEntity(CourseOfferingDTO offeringDTO);

	@Named("mapSemester")
	protected Semester mapSemester(SemesterDTO semesterDTO) {
		return semesterRepository.findById(semesterDTO.getId())
				.orElseThrow(() -> new SemesterNotFoundException(semesterDTO.getId()));
	}

	@Named("mapCourse")
	protected Course mapCourse(CourseDTO courseDTO) {
		return courseRepository.findById(courseDTO.getId())
				.orElseThrow(() -> new CourseNotFoundException(courseDTO.getId()));
	}
}
