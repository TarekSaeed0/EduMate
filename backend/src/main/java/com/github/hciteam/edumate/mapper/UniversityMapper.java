package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.University;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.dto.UniversityDTO;
import com.github.hciteam.edumate.dto.SemesterDTO;
import com.github.hciteam.edumate.exception.SemesterNotFoundException;

@Mapper(componentModel = "spring", uses = {SemesterMapper.class})
public abstract class UniversityMapper {
	@Autowired
	protected SemesterRepository semesterRepository;

	public abstract UniversityDTO toDTO(University university);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "currentSemester", target = "currentSemester",
			qualifiedByName = "mapSemester")
	@Mapping(target = "courses", ignore = true)
	@Mapping(target = "students", ignore = true)
	public abstract University toEntity(UniversityDTO universityDTO);

	@Named("mapSemester")
	protected Semester mapSemester(SemesterDTO semesterDTO) {
		return semesterRepository.findById(semesterDTO.getId())
				.orElseThrow(() -> new SemesterNotFoundException(semesterDTO.getId()));
	}
}
