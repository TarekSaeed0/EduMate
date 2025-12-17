package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.exception.CourseNotFoundException;
import com.github.hciteam.edumate.exception.CourseAlreadyExistsException;
import com.github.hciteam.edumate.mapper.CourseMapper;
import com.github.hciteam.edumate.dto.CourseDTO;
import com.github.hciteam.edumate.repository.CourseRepository;

@Service
public class CourseService {
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;

	public CourseService(CourseRepository courseRepository,
			CourseMapper courseMapper) {
		this.courseRepository = courseRepository;
		this.courseMapper = courseMapper;
	}

	public List<CourseDTO> getCourses() {
		return courseRepository.findAll().stream().map(courseMapper::toDTO)
				.toList();
	}

	public CourseDTO createCourse(CourseDTO courseDTO) {
		if (courseRepository.existsByCode(courseDTO.getCode())) {
			throw new CourseAlreadyExistsException();
		}

		Course course = courseMapper.toEntity(courseDTO);

		return courseMapper.toDTO(courseRepository.save(course));
	}

	public CourseDTO getCourse(Long courseId) {
		return courseRepository.findById(courseId)
				.map(course -> courseMapper.toDTO(course))
				.orElseThrow(() -> new CourseNotFoundException(courseId));
	}

	public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
		Course course = courseRepository.findById(courseId).map(existingCourse -> {
			courseMapper.updateEntityFromDTO(courseDTO, existingCourse);
			return existingCourse;
		}).orElseThrow(() -> new CourseNotFoundException(courseId));

		return courseMapper.toDTO(courseRepository.save(course));
	}

	public void deleteCourse(Long courseId) {
		if (!courseRepository.existsById(courseId)) {
			throw new CourseNotFoundException(courseId);
		}

		courseRepository.deleteById(courseId);
	}
}
