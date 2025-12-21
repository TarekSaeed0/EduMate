package com.github.hciteam.edumate.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
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
import com.github.hciteam.edumate.model.CourseRegistration;
import jakarta.transaction.Transactional;
import com.github.hciteam.edumate.key.StudentTaskKey;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	private final StudentTaskRepository studentTaskRepository;
	private final TaskMapper taskMapper;
    private final CourseRegistrationRepository registrationRepository;

	public TaskService(TaskRepository taskRepository,
                       StudentTaskRepository studentTaskRepository, TaskMapper taskMapper, CourseRegistrationRepository registrationRepository) {
		this.taskRepository = taskRepository;
		this.studentTaskRepository = studentTaskRepository;
		this.taskMapper = taskMapper;
        this.registrationRepository = registrationRepository;
    }

	public List<TaskDTO> getTasks() {
		return taskRepository.findAll().stream().map(taskMapper::toDTO).toList();
	}

    @Transactional // Crucial: This ensures either everything saves or nothing does
    public TaskDTO createTask(TaskDTO taskDTO) {
        // 1. Save the task basic info
        Task task = taskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);

        // 2. Find everyone currently in that class
        List<CourseRegistration> registrations = registrationRepository
                .findByOfferingId(savedTask.getOffering().getId());

        // 3. Create a 'Tracker' entry for every student found
        List<StudentTask> broadcastList = registrations.stream()
                .map(reg -> StudentTask.builder()
                        .id(new StudentTaskKey(reg.getStudent().getId(), savedTask.getId()))
                        .student(reg.getStudent())
                        .task(savedTask)
                        .submittedAt(null) // New tasks start as 'Not submitted'
                        .build())
                .toList();

        // 4. Save all links to the database
        studentTaskRepository.saveAll(broadcastList);

        return taskMapper.toDTO(savedTask);
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
