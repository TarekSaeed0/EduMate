package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.model.CourseMaterial;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.dto.CourseDTO;
import com.github.hciteam.edumate.dto.CourseMaterialDTO;
import com.github.hciteam.edumate.exception.CourseNotFoundException;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public abstract class CourseMaterialMapper {
	@Autowired
	protected CourseRepository courseRepository;

	public abstract CourseMaterialDTO toDTO(CourseMaterial material);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "course", target = "course", qualifiedByName = "mapCourse")
	public abstract CourseMaterial toEntity(CourseMaterialDTO materialDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "course", target = "course", qualifiedByName = "mapCourse")
	public abstract void updateEntityFromDTO(CourseMaterialDTO materialDTO,
			@MappingTarget CourseMaterial material);

	@Named("mapCourse")
	protected Course mapCourse(CourseDTO courseDTO) {
		return courseRepository.findById(courseDTO.getId())
				.orElseThrow(() -> new CourseNotFoundException(courseDTO.getId()));
	}
}
