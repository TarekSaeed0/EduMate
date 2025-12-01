package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.dto.CourseDTO;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	CourseDTO toDTO(Course course);

	@Mapping(target = "offerings", ignore = true)
	Course toEntity(CourseDTO courseDTO);
}
