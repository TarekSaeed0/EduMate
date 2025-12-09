package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.dto.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	@Mapping(source = "user.id", target = "userId")
	StudentDTO toDTO(Student student);

	@Mapping(source = "userId", target = "user.id")
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	Student toEntity(StudentDTO studentDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	public abstract void updateEntityFromDTO(StudentDTO studentDTO,
			@MappingTarget Student student);
}
