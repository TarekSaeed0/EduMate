package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.Semester;
import com.github.hciteam.edumate.model.SemesterDTO;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
	SemesterDTO toDTO(Semester semester);

	@Mapping(target = "courses", ignore = true)
	Semester toEntity(SemesterDTO semesterDTO);
}
