package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.TaskNotFoundException;
import com.github.hciteam.edumate.key.StudentTaskKey;
import com.github.hciteam.edumate.mapper.TaskMapper;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import com.github.hciteam.edumate.dto.TaskDTO;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;
import com.github.hciteam.edumate.repository.TaskRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	private final CourseOfferingRepository offeringRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final TaskMapper taskMapper;

	public TaskService(TaskRepository taskRepository,
			CourseOfferingRepository offeringRepository,
			StudentTaskRepository studentTaskRepository, TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.offeringRepository = offeringRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.taskMapper = taskMapper;
	}

	public List<TaskDTO> getTasks() {
		return taskRepository.findAll().stream().map(task -> taskMapper.toDTO(task))
				.toList();
	}

	@Transactional
	public TaskDTO createTask(TaskDTO taskDTO) {
		CourseOffering offering =
				offeringRepository.findById(taskDTO.getOffering().getId())
						.orElseThrow(() -> new CourseOfferingNotFoundException());

		Task task = new Task(null, offering, taskDTO.getTitle(),
				taskDTO.getRequirements(), taskDTO.getSubmissionUrl(),
				taskDTO.getDueDate(), taskDTO.getNotes(), null);

		Task createdTask = taskRepository.save(task);

		List<StudentTask> studentTasks =
				offering.getRegistrations().stream()
						.filter(registration -> registration
								.getStatus() == CourseRegistrationStatus.REGISTERED)
						.map(registration -> new StudentTask(
								new StudentTaskKey(registration.getStudent().getId(),
										createdTask.getId()),
								registration.getStudent(), task, null))
						.toList();

		studentTaskRepository.saveAll(studentTasks);


		return taskMapper.toDTO(createdTask);
	}

	public TaskDTO getTask(Long taskId) {
		return taskRepository.findById(taskId).map(task -> taskMapper.toDTO(task))
				.orElseThrow(() -> new TaskNotFoundException());
	}

	public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
		Task task = taskRepository.findById(taskId).map(existingTask -> {
			CourseOffering offering =
					offeringRepository.findById(taskDTO.getOffering().getId())
							.orElseThrow(() -> new CourseOfferingNotFoundException());

			existingTask.setOffering(offering);
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
