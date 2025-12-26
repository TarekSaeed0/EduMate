package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.University;
import com.github.hciteam.edumate.repository.UniversityRepository;
import com.github.hciteam.edumate.dto.StudentDTO;
import com.github.hciteam.edumate.dto.UniversityDTO;
import com.github.hciteam.edumate.exception.UniversityNotFoundException;

@Mapper(componentModel = "spring", uses = {UniversityMapper.class})
public abstract class StudentMapper {
	@Autowired
	protected UniversityRepository universityRepository;

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

	@Named("mapUniversity")
	protected University mapUniversity(UniversityDTO universityDTO) {
		return universityRepository.findById(universityDTO.getId()).orElseThrow(
				() -> new UniversityNotFoundException(universityDTO.getId()));
	}
}
