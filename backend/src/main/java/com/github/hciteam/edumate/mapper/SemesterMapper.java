package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.dto.SemesterDTO;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
	SemesterDTO toDTO(Semester semester);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "offerings", ignore = true)
	Semester toEntity(SemesterDTO semesterDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "term", ignore = true)
	@Mapping(target = "year", ignore = true)
	@Mapping(target = "offerings", ignore = true)
	void updateEntityFromDTO(SemesterDTO semesterDTO,
			@MappingTarget Semester semester);
}
