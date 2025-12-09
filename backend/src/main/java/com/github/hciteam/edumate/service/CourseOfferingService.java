package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.exception.SemesterNotFoundException;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.exception.CourseNotFoundException;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.CourseAlreadyExistsException;
import com.github.hciteam.edumate.mapper.CourseOfferingMapper;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;
import com.github.hciteam.edumate.specification.CourseOfferingSpecifications;

@Service
public class CourseOfferingService {
	private final CourseRepository courseRepository;
	private final SemesterRepository semesterRepository;
	private final CourseOfferingRepository offeringRepository;
	private final CourseOfferingMapper offeringMapper;

	public CourseOfferingService(CourseRepository courseRepository,
			SemesterRepository semesterRepository,
			CourseOfferingRepository offeringRepository,
			CourseOfferingMapper offeringMapper) {
		this.courseRepository = courseRepository;
		this.semesterRepository = semesterRepository;
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
				.orElseThrow(() -> new SemesterNotFoundException());
	}

	public void deleteOffering(Long offeringId) {
		if (!offeringRepository.existsById(offeringId)) {
			throw new CourseOfferingNotFoundException();
		}

		offeringRepository.deleteById(offeringId);
	}
}
