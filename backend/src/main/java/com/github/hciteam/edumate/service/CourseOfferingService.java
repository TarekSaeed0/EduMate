package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.CourseAlreadyExistsException;
import com.github.hciteam.edumate.mapper.CourseOfferingMapper;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.specification.CourseOfferingSpecifications;

@Service
public class CourseOfferingService {
	private final CourseOfferingRepository offeringRepository;
	private final CourseOfferingMapper offeringMapper;

	public CourseOfferingService(CourseOfferingRepository offeringRepository,
			CourseOfferingMapper offeringMapper) {
		this.offeringRepository = offeringRepository;
		this.offeringMapper = offeringMapper;
	}

	public List<CourseOfferingDTO> getOfferings(Long semesterId, Long courseId) {
		Specification<CourseOffering> specification = Specification.unrestricted();

		if (semesterId != null) {
			specification = specification
					.and(CourseOfferingSpecifications.ofSemester(semesterId));
		}

		if (courseId != null) {
			specification =
					specification.and(CourseOfferingSpecifications.ofCourse(courseId));
		}

		return offeringRepository.findAll(specification).stream()
				.map(offeringMapper::toDTO).toList();
	}

	public CourseOfferingDTO createOffering(CourseOfferingDTO offeringDTO) {
		if (offeringRepository.existsBySemesterIdAndCourseId(
				offeringDTO.getSemesterId(), offeringDTO.getCourse().getId())) {
			throw new CourseAlreadyExistsException();
		}

		CourseOffering offering = offeringMapper.toEntity(offeringDTO);

		return offeringMapper.toDTO(offeringRepository.save(offering));
	}

	public CourseOfferingDTO getOffering(Long offeringId) {
		return offeringRepository.findById(offeringId).map(offeringMapper::toDTO)
				.orElseThrow(() -> new CourseOfferingNotFoundException(offeringId));
	}

	public void deleteOffering(Long offeringId) {
		if (!offeringRepository.existsById(offeringId)) {
			throw new CourseOfferingNotFoundException(offeringId);
		}

		offeringRepository.deleteById(offeringId);
	}
}
