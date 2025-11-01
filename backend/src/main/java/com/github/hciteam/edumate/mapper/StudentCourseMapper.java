package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.StudentCourse;
import com.github.hciteam.edumate.model.StudentCourseDTO;

@Mapper(componentModel = "spring", uses = {SemesterCourseMapper.class})
public interface StudentCourseMapper {
	@Mapping(source = "student.id", target = "studentId")
	StudentCourseDTO toDTO(StudentCourse studentCourse);

	@Mapping(source = "studentId", target = "id.studentId")
	@Mapping(source = "studentId", target = "student.id")
	StudentCourse toEntity(StudentCourseDTO studentCourseDTO);
}
