package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.CourseRegistrationDTO;
import com.github.hciteam.edumate.exception.StudentNotFoundException;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.CourseRegistrationAlreadyExistsException;
import com.github.hciteam.edumate.exception.CourseRegistrationNotFoundException;
import com.github.hciteam.edumate.mapper.CourseRegistrationMapper;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.specification.CourseRegistrationSpecifications;

@Service
public class CourseRegistrationService {
	private final CourseOfferingRepository offeringRepository;
	private final StudentRepository studentRepository;
	private final CourseRegistrationRepository registrationRepository;
	private final CourseRegistrationMapper registrationMapper;

	public CourseRegistrationService(CourseOfferingRepository offeringRepository,
			StudentRepository studentRepository,
			CourseRegistrationRepository registrationRepository,
			CourseRegistrationMapper registrationMapper) {
		this.offeringRepository = offeringRepository;
		this.studentRepository = studentRepository;
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
		Student student = studentRepository.findById(registrationDTO.getStudentId())
				.orElseThrow(() -> new StudentNotFoundException());
		CourseOffering offering =
				offeringRepository.findById(registrationDTO.getOffering().getId())
						.orElseThrow(() -> new CourseOfferingNotFoundException());

		if (registrationRepository.existsByOfferingIdAndStudentId(
				registrationDTO.getOffering().getId(), student.getId())) {
			throw new CourseRegistrationAlreadyExistsException();
		}

		CourseRegistration registration = new CourseRegistration(null, offering,
				student, CourseRegistrationStatus.REGISTERED);

		return registrationMapper.toDTO(registrationRepository.save(registration));
	}

	public CourseRegistrationDTO getRegistration(Long registrationId) {
		return registrationRepository.findById(registrationId)
				.map(registrationMapper::toDTO)
				.orElseThrow(() -> new CourseRegistrationNotFoundException());
	}

	public CourseRegistrationDTO updateRegistration(Long registrationId,
			CourseRegistrationDTO registrationDTO) {
		CourseRegistration registration = registrationRepository
				.findById(registrationId).map(existingSudentCourse -> {
					existingSudentCourse.setStatus(registrationDTO.getStatus());
					return existingSudentCourse;
				}).orElseThrow(() -> new CourseRegistrationNotFoundException());

		return registrationMapper.toDTO(registrationRepository.save(registration));
	}

	public void deleteRegistration(Long registrationId) {
		if (!registrationRepository.existsById(registrationId)) {
			throw new CourseRegistrationNotFoundException();
		}

		registrationRepository.deleteById(registrationId);
	}
}
