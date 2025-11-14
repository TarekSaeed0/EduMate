package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.SemesterCourse;
import com.github.hciteam.edumate.entity.Task;
import com.github.hciteam.edumate.exception.SemesterCourseNotFoundException;
import com.github.hciteam.edumate.exception.TaskNotFoundException;
import com.github.hciteam.edumate.mapper.TaskMapper;
import com.github.hciteam.edumate.model.TaskDTO;
import com.github.hciteam.edumate.repository.SemesterCourseRepository;
import com.github.hciteam.edumate.repository.TaskRepository;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	private final SemesterCourseRepository semesterCourseRepository;
	private final TaskMapper taskMapper;

	public TaskService(TaskRepository taskRepository,
			SemesterCourseRepository semesterCourseRepository,
			TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.semesterCourseRepository = semesterCourseRepository;
		this.taskMapper = taskMapper;
	}

	public List<TaskDTO> getTasks() {
		return taskRepository.findAll().stream().map(task -> taskMapper.toDTO(task))
				.toList();
	}

	public TaskDTO createTask(TaskDTO taskDTO) {
		SemesterCourse semesterCourse =
				semesterCourseRepository.findById(taskDTO.getSemesterCourse().getId())
						.orElseThrow(() -> new SemesterCourseNotFoundException());

		Task task = new Task(null, semesterCourse, taskDTO.getTitle(),
				taskDTO.getRequirements(), taskDTO.getSubmissionUrl(),
				taskDTO.getDueDate(), taskDTO.getNotes());

		return taskMapper.toDTO(taskRepository.save(task));
	}

	public TaskDTO getTask(Long taskId) {
		return taskRepository.findById(taskId).map(task -> taskMapper.toDTO(task))
				.orElseThrow(() -> new TaskNotFoundException());
	}

	public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
		Task task = taskRepository.findById(taskId).map(existingTask -> {
			SemesterCourse semesterCourse =
					semesterCourseRepository.findById(taskDTO.getSemesterCourse().getId())
							.orElseThrow(() -> new SemesterCourseNotFoundException());

			existingTask.setSemesterCourse(semesterCourse);
			existingTask.setTitle(taskDTO.getTitle());
			existingTask.setRequirements(taskDTO.getRequirements());
			existingTask.setSubmissionUrl(taskDTO.getSubmissionUrl());
			existingTask.setDueDate(taskDTO.getDueDate());
			existingTask.setNotes(taskDTO.getNotes());
			return existingTask;
		}).orElseThrow(() -> new TaskNotFoundException());

		return taskMapper.toDTO(taskRepository.save(task));
	}

	public void deleteTask(Long taskId) {
		if (!taskRepository.existsById(taskId)) {
			throw new TaskNotFoundException();
		}

		taskRepository.deleteById(taskId);
	}
}
