package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.Course;
import com.github.hciteam.edumate.entity.Semester;
import com.github.hciteam.edumate.entity.SemesterCourse;
import com.github.hciteam.edumate.exception.CourseNotFoundException;
import com.github.hciteam.edumate.exception.SemesterAlreadyExistsException;
import com.github.hciteam.edumate.exception.SemesterNotFoundException;
import com.github.hciteam.edumate.mapper.SemesterCourseMapper;
import com.github.hciteam.edumate.mapper.SemesterMapper;
import com.github.hciteam.edumate.model.SemesterCourseDTO;
import com.github.hciteam.edumate.model.SemesterDTO;
import com.github.hciteam.edumate.repository.CourseRepository;
import com.github.hciteam.edumate.repository.SemesterCourseRepository;
import com.github.hciteam.edumate.repository.SemesterRepository;

@Service
public class SemesterService {
	private final SemesterRepository semesterRepository;
	private final CourseRepository courseRepository;
	private final SemesterCourseRepository semesterCourseRepository;
	private final SemesterMapper semesterMapper;
	private final SemesterCourseMapper semesterCourseMapper;

	public SemesterService(SemesterRepository semesterRepository,
			CourseRepository courseRepository,
			SemesterCourseRepository semesterCourseRepository,
			SemesterMapper semesterMapper,
			SemesterCourseMapper semesterCourseMapper) {
		this.semesterRepository = semesterRepository;
		this.courseRepository = courseRepository;
		this.semesterCourseRepository = semesterCourseRepository;
		this.semesterMapper = semesterMapper;
		this.semesterCourseMapper = semesterCourseMapper;
	}

	public List<SemesterDTO> getSemesters() {
		return semesterRepository.findAll().stream()
				.map(semester -> semesterMapper.toDTO(semester)).toList();
	}

	public SemesterDTO createSemester(SemesterDTO semesterDTO) {
		if (semesterRepository.existsByTermAndYear(semesterDTO.getTerm(),
				semesterDTO.getYear())) {
			throw new SemesterAlreadyExistsException();
		}

		Semester semester =
				new Semester(null, semesterDTO.getTerm(), semesterDTO.getYear(),
						semesterDTO.getStartDate(), semesterDTO.getEndDate(), null);

		return semesterMapper.toDTO(semesterRepository.save(semester));
	}

	public SemesterDTO getSemester(Long semesterId) {
		return semesterRepository.findById(semesterId)
				.map(semester -> semesterMapper.toDTO(semester))
				.orElseThrow(() -> new SemesterNotFoundException());
	}

	public SemesterDTO updateSemester(Long semesterId, SemesterDTO semesterDTO) {
		Semester semester =
				semesterRepository.findById(semesterId).map(existingSemester -> {
					existingSemester.setStartDate(semesterDTO.getStartDate());
					existingSemester.setEndDate(semesterDTO.getEndDate());
					return existingSemester;
				}).orElseThrow(() -> new SemesterNotFoundException());

		return semesterMapper.toDTO(semesterRepository.save(semester));
	}

	public void deleteSemester(Long semesterId) {
		if (!semesterRepository.existsById(semesterId)) {
			throw new SemesterNotFoundException();
		}

		semesterRepository.deleteById(semesterId);
	}

	public List<SemesterCourseDTO> getSemesterCourses(Long semesterId) {
		if (!semesterRepository.existsById(semesterId)) {
			throw new SemesterNotFoundException();
		}

		return semesterCourseRepository.findBySemesterId(semesterId).stream()
				.map(semesterCourse -> semesterCourseMapper.toDTO(semesterCourse))
				.toList();
	}

	public SemesterCourseDTO createSemesterCourse(Long semesterId,
			SemesterCourseDTO semesterCourseDTO) {
		Semester semester = semesterRepository.findById(semesterId)
				.orElseThrow(() -> new SemesterNotFoundException());
		Course course =
				courseRepository.findById(semesterCourseDTO.getCourse().getId())
						.orElseThrow(() -> new CourseNotFoundException());

		SemesterCourse semesterCourse =
				new SemesterCourse(null, semester, course, null, null);

		return semesterCourseMapper
				.toDTO(semesterCourseRepository.save(semesterCourse));
	}

	public SemesterCourseDTO getSemesterCourse(Long semesterId, Long courseId) {
		if (!semesterRepository.existsById(semesterId)) {
			throw new SemesterNotFoundException();
		}

		return semesterCourseRepository
				.findBySemesterIdAndCourseId(semesterId, courseId)
				.map(semesterCourse -> semesterCourseMapper.toDTO(semesterCourse))
				.orElseThrow(() -> new SemesterNotFoundException());
	}

	public SemesterCourseDTO updateSemesterCourse(Long semesterId, Long courseId,
			SemesterCourseDTO semesterCourseDTO) {
		if (!semesterRepository.existsById(semesterId)) {
			throw new SemesterNotFoundException();
		}

		SemesterCourse semesterCourse = semesterCourseRepository
				.findBySemesterIdAndCourseId(semesterId, courseId)
				.map(existingSemesterCourse -> {
					return existingSemesterCourse;
				}).orElseThrow(() -> new SemesterNotFoundException());

		return semesterCourseMapper
				.toDTO(semesterCourseRepository.save(semesterCourse));
	}

	public void deleteSemesterCourse(Long semesterId, Long courseId) {
		if (!semesterRepository.existsById(semesterId)) {
			throw new SemesterNotFoundException();
		}

		if (!semesterCourseRepository.existsBySemesterIdAndCourseId(semesterId,
				courseId)) {
			throw new SemesterNotFoundException();
		}

		semesterCourseRepository.deleteBySemesterIdAndCourseId(semesterId,
				courseId);
	}
}
