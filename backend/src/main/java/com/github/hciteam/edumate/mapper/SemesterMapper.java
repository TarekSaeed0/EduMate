package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.dto.SemesterDTO;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
	SemesterDTO toDTO(Semester semester);

	@Mapping(target = "offerings", ignore = true)
	Semester toEntity(SemesterDTO semesterDTO);
}
