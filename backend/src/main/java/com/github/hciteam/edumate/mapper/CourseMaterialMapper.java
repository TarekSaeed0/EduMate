package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.CourseMaterial;
import com.github.hciteam.edumate.dto.CourseMaterialDTO;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface CourseMaterialMapper {
	CourseMaterialDTO toDTO(CourseMaterial material);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "course", target = "course", qualifiedByName = "mapCourse")
	CourseMaterial toEntity(CourseMaterialDTO materialDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "course", target = "course", qualifiedByName = "mapCourse")
	void updateEntityFromDTO(CourseMaterialDTO materialDTO,
			@MappingTarget CourseMaterial material);
}
