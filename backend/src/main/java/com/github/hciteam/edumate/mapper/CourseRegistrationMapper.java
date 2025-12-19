package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.dto.CourseRegistrationDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.StudentNotFoundException;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public abstract class CourseRegistrationMapper {
	@Autowired
	protected CourseOfferingRepository offeringRepository;
	@Autowired
	protected StudentRepository studentRepository;

	@Mapping(source = "student.id", target = "studentId")
	public abstract CourseRegistrationDTO toDTO(CourseRegistration registration);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "studentId", target = "student",
			qualifiedByName = "mapStudent")
	public abstract CourseRegistration toEntity(
			CourseRegistrationDTO registrationDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "offering", ignore = true)
	@Mapping(target = "student", ignore = true)
	public abstract void updateEntityFromDTO(
			CourseRegistrationDTO registrationDTO,
			@MappingTarget CourseRegistration registration);

	@Named("mapOffering")
	protected CourseOffering mapOffering(CourseOfferingDTO offeringDTO) {
		return offeringRepository.findById(offeringDTO.getId()).orElseThrow(
				() -> new CourseOfferingNotFoundException(offeringDTO.getId()));
	}

	@Named("mapStudent")
	protected Student mapStudent(Long studentId) {
		return studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotFoundException());
	}
}
