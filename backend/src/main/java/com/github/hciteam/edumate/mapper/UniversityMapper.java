package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.University;
import com.github.hciteam.edumate.repository.UniversityRepository;
import com.github.hciteam.edumate.dto.UniversityDTO;
import com.github.hciteam.edumate.exception.UniversityNotFoundException;

@Mapper(componentModel = "spring", uses = {SemesterMapper.class})
public abstract class UniversityMapper {
	@Autowired
	protected UniversityRepository universityRepository;

	public abstract UniversityDTO toDTO(University university);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "currentSemester", target = "currentSemester",
			qualifiedByName = "mapSemester")
	@Mapping(target = "courses", ignore = true)
	@Mapping(target = "students", ignore = true)
	public abstract University toEntity(UniversityDTO universityDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "currentSemester", target = "currentSemester",
			qualifiedByName = "mapSemester")
	@Mapping(target = "courses", ignore = true)
	@Mapping(target = "students", ignore = true)
	public abstract void updateEntityFromDTO(UniversityDTO universityDTO,
			@MappingTarget University university);

	@Named("mapUniversity")
	protected University mapUniversity(UniversityDTO universityDTO) {
		return universityRepository.findById(universityDTO.getId()).orElseThrow(
				() -> new UniversityNotFoundException(universityDTO.getId()));
	}
}
