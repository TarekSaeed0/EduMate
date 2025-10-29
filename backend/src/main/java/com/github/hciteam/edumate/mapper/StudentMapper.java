package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.model.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	@Mapping(source = "user.id", target = "userId")
	StudentDTO toDTO(Student student);

	@Mapping(source = "userId", target = "user.id")
	@Mapping(target = "studentCourses", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	Student toEntity(StudentDTO studentDTO);
}
