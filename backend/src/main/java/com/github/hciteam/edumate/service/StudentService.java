package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.SemesterCourse;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.StudentCourse;
import com.github.hciteam.edumate.entity.StudentTask;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.exception.SemesterCourseNotFoundException;
import com.github.hciteam.edumate.exception.StudentCourseAlreadyExists;
import com.github.hciteam.edumate.exception.StudentCourseNotFoundException;
import com.github.hciteam.edumate.exception.StudentNotFoundException;
import com.github.hciteam.edumate.key.StudentCourseKey;
import com.github.hciteam.edumate.mapper.StudentCourseMapper;
import com.github.hciteam.edumate.mapper.StudentMapper;
import com.github.hciteam.edumate.mapper.StudentTaskMapper;
import com.github.hciteam.edumate.model.StudentCourseDTO;
import com.github.hciteam.edumate.model.StudentCourseStatus;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.model.StudentTaskDTO;
import com.github.hciteam.edumate.model.StudentTaskStatus;
import com.github.hciteam.edumate.repository.SemesterCourseRepository;
import com.github.hciteam.edumate.repository.StudentCourseRepository;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.specification.StudentTaskSpecifications;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final StudentCourseRepository studentCourseRepository;
	private final SemesterCourseRepository semesterCourseRepository;
	private final StudentMapper studentMapper;
	private final StudentTaskMapper studentTaskMapper;
	private final StudentCourseMapper studentCourseMapper;

	public StudentService(StudentRepository studentRepository,
			StudentTaskRepository studentTaskRepository,
			StudentCourseRepository studentCourseRepository,
			SemesterCourseRepository semesterCourseRepository,
			StudentMapper studentMapper, StudentTaskMapper studentTaskMapper,
			StudentCourseMapper studentCourseMapper) {
		this.studentRepository = studentRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.studentCourseRepository = studentCourseRepository;
		this.semesterCourseRepository = semesterCourseRepository;
		this.studentMapper = studentMapper;
		this.studentTaskMapper = studentTaskMapper;
		this.studentCourseMapper = studentCourseMapper;
	}

	public StudentDTO getStudent(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotFoundException());
		return studentMapper.toDTO(student);
	}

	public List<StudentTaskDTO> getStudentTasks(Long studentId, Long courseId,
			StudentTaskStatus status) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}

		Specification<StudentTask> specification =
				StudentTaskSpecifications.ofStudent(studentId);

		if (courseId != null) {
			specification =
					specification.and(StudentTaskSpecifications.ofCourse(courseId));
		}

		if (status != null) {
			specification = specification.and(switch (status) {
				case UPCOMING -> StudentTaskSpecifications.isUpcoming();
				case OVERDUE -> StudentTaskSpecifications.isOverdue();
				case COMPLETED -> StudentTaskSpecifications.isCompleted();
			});
		}

		return studentTaskRepository.findAll(specification).stream()
				.map(task -> studentTaskMapper.toDTO(task)).toList();
	}

	public List<StudentCourseDTO> getStudentCourses(Long studentId) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}

		return studentCourseRepository.findByStudentId(studentId).stream()
				.map(course -> studentCourseMapper.toDTO(course)).toList();
	}

	public StudentCourseDTO createStudentCourse(Long studentId,
			StudentCourseDTO studentCourseDTO) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotFoundException());
		SemesterCourse semesterCourse = semesterCourseRepository
				.findById(studentCourseDTO.getSemesterCourse().getId())
				.orElseThrow(() -> new SemesterCourseNotFoundException());


		StudentCourseKey studentCourseId = new StudentCourseKey(studentId,
				studentCourseDTO.getSemesterCourse().getId());

		if (studentCourseRepository.existsById(studentCourseId)) {
			throw new StudentCourseAlreadyExists();
		}

		StudentCourse studentCourse = new StudentCourse(studentCourseId, student,
				semesterCourse, StudentCourseStatus.REGISTERED);

		return studentCourseMapper
				.toDTO(studentCourseRepository.save(studentCourse));
	}

	public void deleteStudentCourse(Long studentId, Long semesterCourseId) {
		StudentCourseKey studentCourseId =
				new StudentCourseKey(studentId, semesterCourseId);

		if (!studentCourseRepository.existsById(studentCourseId)) {
			throw new StudentCourseNotFoundException();
		}

		studentCourseRepository.deleteById(studentCourseId);
	}

	public StudentDTO getCurrentStudent(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFoundException());

		return studentMapper.toDTO(student);
	}

	public List<StudentTaskDTO> getCurrentStudentTasks(
			Authentication authentication, Long courseId, StudentTaskStatus status) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFoundException());

		return getStudentTasks(student.getId(), courseId, status);
	}

	public List<StudentCourseDTO> getCurrentStudentCourses(
			Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFoundException());

		return getStudentCourses(student.getId());
	}

	public StudentCourseDTO createCurrentStudentCourse(
			Authentication authentication, StudentCourseDTO studentCourseDTO) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFoundException());

		return createStudentCourse(student.getId(), studentCourseDTO);
	}

	public void deleteCurrentStudentCourse(Authentication authentication,
			Long semesterCourseId) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFoundException());

		deleteStudentCourse(student.getId(), semesterCourseId);
	}
}
