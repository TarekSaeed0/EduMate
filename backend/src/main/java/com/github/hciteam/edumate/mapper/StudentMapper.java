package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.dto.StudentDTO;
import com.github.hciteam.edumate.exception.StudentNotFoundException;

@Mapper(componentModel = "spring", uses = {UniversityMapper.class})
public abstract class StudentMapper {
	@Autowired
	protected StudentRepository studentRepository;

	@Mapping(source = "user.id", target = "userId")
	public abstract StudentDTO toDTO(Student student);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "university", target = "university",
			qualifiedByName = "mapUniversity")
	@Mapping(source = "userId", target = "user.id")
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	public abstract Student toEntity(StudentDTO studentDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "university", target = "university",
			qualifiedByName = "mapUniversity")
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "registrations", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	public abstract void updateEntityFromDTO(StudentDTO studentDTO,
			@MappingTarget Student student);

	@Named("mapStudent")
	protected Student mapStudent(StudentDTO studentDTO) {
		return studentRepository.findById(studentDTO.getId())
				.orElseThrow(() -> new StudentNotFoundException(studentDTO.getId()));
	}
}
