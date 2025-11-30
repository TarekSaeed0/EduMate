package com.github.hciteam.edumate.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.TaskDTO;
import com.github.hciteam.edumate.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping
	public ResponseEntity<List<TaskDTO>> getTasks() {
		return ResponseEntity.ok(taskService.getTasks());
	}

	@PostMapping
	public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
		TaskDTO createdTask = taskService.createTask(taskDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{taskId}").buildAndExpand(createdTask.getId()).toUri();

		return ResponseEntity.created(location).body(createdTask);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
		return ResponseEntity.ok(taskService.getTask(taskId));
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId,
			@RequestBody TaskDTO taskDTO) {
		return ResponseEntity.ok(taskService.updateTask(taskId, taskDTO));
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
		taskService.deleteTask(taskId);

		return ResponseEntity.noContent().build();
	}

}
