package com.github.hciteam.edumate.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.exception.TaskNotFoundException;
import com.github.hciteam.edumate.key.StudentTaskKey;
import com.github.hciteam.edumate.mapper.TaskMapper;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import com.github.hciteam.edumate.dto.TaskDTO;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.repository.TaskRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final TaskMapper taskMapper;

	public TaskService(TaskRepository taskRepository,
			StudentTaskRepository studentTaskRepository, TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.taskMapper = taskMapper;
	}

	public List<TaskDTO> getTasks() {
		return taskRepository.findAll().stream().map(taskMapper::toDTO).toList();
	}

	@Transactional
	public TaskDTO createTask(TaskDTO taskDTO) {
		Task task = taskMapper.toEntity(taskDTO);

		Task persistedTask = taskRepository.save(task);

		Set<StudentTask> studentTasks =
				persistedTask.getOffering().getRegistrations().stream()
						.filter(registration -> registration
								.getStatus() == CourseRegistrationStatus.REGISTERED)
						.map(registration -> StudentTask.builder()
								.id(new StudentTaskKey(registration.getStudent().getId(),
										persistedTask.getId()))
								.student(registration.getStudent()).task(persistedTask).build())
						.collect(Collectors.toSet());

		studentTaskRepository.saveAll(studentTasks);

		return taskMapper.toDTO(persistedTask);
	}

	public TaskDTO getTask(Long taskId) {
		return taskRepository.findById(taskId).map(taskMapper::toDTO)
				.orElseThrow(() -> new TaskNotFoundException());
	}

	@Transactional
	public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
		Task task = taskRepository.findById(taskId).map(existingTask -> {
			boolean offeringChanged = !existingTask.getOffering().getId()
					.equals(taskDTO.getOffering().getId());

			taskMapper.updateEntityFromDTO(taskDTO, existingTask);

			Task persistedTask = taskRepository.save(existingTask);

			if (offeringChanged) {
				studentTaskRepository.deleteAll(persistedTask.getStudentTasks());

				Set<StudentTask> studentTasks = persistedTask.getOffering()
						.getRegistrations().stream()
						.filter(registration -> registration
								.getStatus() == CourseRegistrationStatus.REGISTERED)
						.map(registration -> StudentTask.builder()
								.id(new StudentTaskKey(registration.getStudent().getId(),
										persistedTask.getId()))
								.student(registration.getStudent()).task(persistedTask).build())
						.collect(Collectors.toSet());

				studentTaskRepository.saveAll(studentTasks);
			}

			return persistedTask;
		}).orElseThrow(() -> new TaskNotFoundException());

		return taskMapper.toDTO(task);
	}

	public void deleteTask(Long taskId) {
		if (!taskRepository.existsById(taskId)) {
			throw new TaskNotFoundException();
		}

		taskRepository.deleteById(taskId);
	}
}
