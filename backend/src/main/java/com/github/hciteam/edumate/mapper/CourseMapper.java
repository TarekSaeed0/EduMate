package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.Course;
import com.github.hciteam.edumate.model.CourseDTO;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	CourseDTO toDTO(Course course);

	@Mapping(target = "semesterCourses", ignore = true)
	Course toEntity(CourseDTO courseDTO);
}
