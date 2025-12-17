package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.CourseRegistrationDTO;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import com.github.hciteam.edumate.exception.CourseRegistrationAlreadyExistsException;
import com.github.hciteam.edumate.exception.CourseRegistrationNotFoundException;
import com.github.hciteam.edumate.mapper.CourseRegistrationMapper;
import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
import com.github.hciteam.edumate.specification.CourseRegistrationSpecifications;

@Service
public class CourseRegistrationService {
	private final CourseRegistrationRepository registrationRepository;
	private final CourseRegistrationMapper registrationMapper;

	public CourseRegistrationService(
			CourseRegistrationRepository registrationRepository,
			CourseRegistrationMapper registrationMapper) {
		this.registrationRepository = registrationRepository;
		this.registrationMapper = registrationMapper;
	}

	public List<CourseRegistrationDTO> getRegistrations(Long offeringId,
			Long semesterId, Long courseId, Long studentId,
			CourseRegistrationStatus status) {
		Specification<CourseRegistration> specification =
				Specification.unrestricted();

		if (offeringId != null) {
			specification = specification
					.and(CourseRegistrationSpecifications.ofOffering(offeringId));
		}

		if (semesterId != null) {
			specification = specification
					.and(CourseRegistrationSpecifications.ofSemester(semesterId));
		}

		if (courseId != null) {
			specification = specification
					.and(CourseRegistrationSpecifications.ofCourse(courseId));
		}

		if (studentId != null) {
			specification = specification
					.and(CourseRegistrationSpecifications.ofStudent(studentId));
		}

		if (status != null) {
			specification =
					specification.and(CourseRegistrationSpecifications.ofStatus(status));
		}

		return registrationRepository.findAll(specification).stream()
				.map(registrationMapper::toDTO).toList();
	}

	public CourseRegistrationDTO createRegistration(
			CourseRegistrationDTO registrationDTO) {
		if (registrationRepository.existsByOfferingIdAndStudentId(
				registrationDTO.getOffering().getId(),
				registrationDTO.getStudentId())) {
			throw new CourseRegistrationAlreadyExistsException();
		}

		CourseRegistration registration =
				registrationMapper.toEntity(registrationDTO);

		return registrationMapper.toDTO(registrationRepository.save(registration));
	}

	public CourseRegistrationDTO getRegistration(Long registrationId) {
		return registrationRepository.findById(registrationId)
				.map(registrationMapper::toDTO).orElseThrow(
						() -> new CourseRegistrationNotFoundException(registrationId));
	}

	public CourseRegistrationDTO updateRegistration(Long registrationId,
			CourseRegistrationDTO registrationDTO) {
		CourseRegistration registration = registrationRepository
				.findById(registrationId).map(existingSudentCourse -> {
					registrationMapper.updateEntityFromDTO(registrationDTO,
							existingSudentCourse);
					return existingSudentCourse;
				}).orElseThrow(
						() -> new CourseRegistrationNotFoundException(registrationId));

		return registrationMapper.toDTO(registrationRepository.save(registration));
	}

	public void deleteRegistration(Long registrationId) {
		if (!registrationRepository.existsById(registrationId)) {
			throw new CourseRegistrationNotFoundException(registrationId);
		}

		registrationRepository.deleteById(registrationId);
	}
}
