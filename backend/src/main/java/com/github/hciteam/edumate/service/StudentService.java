package com.github.hciteam.edumate.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.exception.StudentNotFoundException;
import com.github.hciteam.edumate.exception.StudentTaskAlreadySubmittedException;
import com.github.hciteam.edumate.exception.StudentTaskNotFoundException;
import com.github.hciteam.edumate.exception.StudentTaskNotSubmittedException;
import com.github.hciteam.edumate.key.StudentTaskKey;
import com.github.hciteam.edumate.mapper.StudentMapper;
import com.github.hciteam.edumate.mapper.StudentTaskMapper;
import com.github.hciteam.edumate.dto.StudentDTO;
import com.github.hciteam.edumate.dto.StudentTaskDTO;
import com.github.hciteam.edumate.model.StudentTaskStatus;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.specification.StudentTaskSpecifications;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final StudentMapper studentMapper;
	private final StudentTaskMapper studentTaskMapper;

	public StudentService(StudentRepository studentRepository,
			StudentTaskRepository studentTaskRepository, StudentMapper studentMapper,
			StudentTaskMapper studentTaskMapper) {
		this.studentRepository = studentRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.studentMapper = studentMapper;
		this.studentTaskMapper = studentTaskMapper;
	}

	public StudentDTO getStudent(Long studentId) {
		return studentRepository.findById(studentId).map(studentMapper::toDTO)
				.orElseThrow(() -> new StudentNotFoundException());
	}

	public List<StudentTaskDTO> getStudentTasks(Long studentId, Long taskId,
			Long semesterId, Long courseId, StudentTaskStatus status) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}

		Specification<StudentTask> specification =
				StudentTaskSpecifications.ofStudent(studentId);

		if (taskId != null) {
			specification =
					specification.and(StudentTaskSpecifications.ofTask(taskId));
		}

		if (semesterId != null) {
			specification =
					specification.and(StudentTaskSpecifications.ofSemester(semesterId));
		}

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
				.map(studentTaskMapper::toDTO).toList();
	}

	public StudentTaskDTO getStudentTask(Long studentId, Long taskId) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}

		StudentTaskKey studentTaskId = new StudentTaskKey(studentId, taskId);

		return studentTaskRepository.findById(studentTaskId)
				.map(studentTaskMapper::toDTO)
				.orElseThrow(() -> new StudentTaskNotFoundException());
	}

	public StudentTaskDTO submitStudentTask(Long studentId, Long taskId) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}

		StudentTaskKey studentTaskId = new StudentTaskKey(studentId, taskId);

		StudentTask studentTask = studentTaskRepository.findById(studentTaskId)
				.map(existingStudentTask -> {
					if (existingStudentTask.getSubmittedAt() != null) {
						throw new StudentTaskAlreadySubmittedException(
								"Cannot submit a student task that is already submitted");
					}

					existingStudentTask.setSubmittedAt(LocalDateTime.now());
					return existingStudentTask;
				}).orElseThrow(() -> new StudentTaskNotFoundException());

		return studentTaskMapper.toDTO(studentTaskRepository.save(studentTask));
	}

	public StudentTaskDTO unsubmitStudentTask(Long studentId, Long taskId) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}

		StudentTaskKey studentTaskId = new StudentTaskKey(studentId, taskId);

		StudentTask studentTask = studentTaskRepository.findById(studentTaskId)
				.map(existingStudentTask -> {
					if (existingStudentTask.getSubmittedAt() == null) {
						throw new StudentTaskNotSubmittedException(
								"Cannot unsubmit a student task that is not submitted");
					}

					existingStudentTask.setSubmittedAt(null);
					return existingStudentTask;
				}).orElseThrow(() -> new StudentTaskNotFoundException());

		return studentTaskMapper.toDTO(studentTaskRepository.save(studentTask));
	}
}
