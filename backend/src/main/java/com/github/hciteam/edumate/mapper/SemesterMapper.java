package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.dto.SemesterDTO;
import com.github.hciteam.edumate.exception.SemesterNotFoundException;

@Mapper(componentModel = "spring")
public abstract class SemesterMapper {
	@Autowired
	protected SemesterRepository semesterRepository;

	public abstract SemesterDTO toDTO(Semester semester);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "offerings", ignore = true)
	public abstract Semester toEntity(SemesterDTO semesterDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "term", ignore = true)
	@Mapping(target = "year", ignore = true)
	@Mapping(target = "offerings", ignore = true)
	public abstract void updateEntityFromDTO(SemesterDTO semesterDTO,
			@MappingTarget Semester semester);

	@Named("mapSemester")
	protected Semester mapSemester(SemesterDTO semesterDTO) {
		return semesterRepository.findById(semesterDTO.getId())
				.orElseThrow(() -> new SemesterNotFoundException(semesterDTO.getId()));
	}
}
