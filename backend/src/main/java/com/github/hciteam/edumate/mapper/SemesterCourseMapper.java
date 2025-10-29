package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.SemesterCourse;
import com.github.hciteam.edumate.model.SemesterCourseDTO;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SemesterCourseMapper {
	@Mapping(source = "semester.id", target = "semesterId")
	SemesterCourseDTO toDTO(SemesterCourse semesterCourse);

	@Mapping(source = "semesterId", target = "semester.id")
	@Mapping(target = "studentCourses", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	SemesterCourse toEntity(SemesterCourseDTO semesterCourseDTO);
}
