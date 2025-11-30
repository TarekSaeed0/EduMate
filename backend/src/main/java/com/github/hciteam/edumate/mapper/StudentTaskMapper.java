package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.dto.StudentTaskDTO;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface StudentTaskMapper {
	@Mapping(source = "student.id", target = "studentId")
	StudentTaskDTO toDTO(StudentTask studentTask);

	@Mapping(source = "studentId", target = "id.studentId")
	@Mapping(source = "studentId", target = "student.id")
	StudentTask toEntity(StudentTaskDTO studentTaskDTO);
}
