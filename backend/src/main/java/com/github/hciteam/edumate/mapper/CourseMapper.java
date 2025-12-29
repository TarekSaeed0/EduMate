package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.dto.AnnouncementScopeDTO;
import com.github.hciteam.edumate.dto.CourseDTO;
import com.github.hciteam.edumate.exception.CourseNotFoundException;

@Mapper(componentModel = "spring", uses = {UniversityMapper.class})
public abstract class CourseMapper implements AnnouncementScopeMapper {
	@Autowired
	protected CourseRepository courseRepository;

	public abstract CourseDTO toDTO(Course course);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "university", target = "university",
			qualifiedByName = "mapUniversity")
	@Mapping(target = "offerings", ignore = true)
	@Mapping(target = "materials", ignore = true)
	public abstract Course toEntity(CourseDTO courseDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "university", target = "university",
			qualifiedByName = "mapUniversity")
	@Mapping(target = "offerings", ignore = true)
	@Mapping(target = "materials", ignore = true)
	public abstract void updateEntityFromDTO(CourseDTO courseDTO,
			@MappingTarget Course course);

	@Named("mapCourse")
	protected Course mapCourse(CourseDTO courseDTO) {
		return courseRepository.findById(courseDTO.getId())
				.orElseThrow(() -> new CourseNotFoundException(courseDTO.getId()));
	}

	@Override
	public String getScopeType() {
		return "COURSE";
	}

	@Override
	public AnnouncementScopeDTO toDTO(AnnouncementScope scope) {
		return (AnnouncementScopeDTO) toDTO((Course) scope);
	}
}
