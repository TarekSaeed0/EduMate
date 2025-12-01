package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.dto.CourseRegistrationDTO;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public interface CourseRegistrationMapper {
	@Mapping(source = "student.id", target = "studentId")
	CourseRegistrationDTO toDTO(CourseRegistration registration);

	@Mapping(source = "studentId", target = "student.id")
	CourseRegistration toEntity(CourseRegistrationDTO registrationDTO);
}
