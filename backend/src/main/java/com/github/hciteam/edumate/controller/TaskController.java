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
import com.github.hciteam.edumate.model.TaskDTO;
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
				.path("/{id}").buildAndExpand(createdTask.getId()).toUri();

		return ResponseEntity.created(location).body(createdTask);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
		return ResponseEntity.ok(taskService.getTask(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id,
			@RequestBody TaskDTO taskDTO) {
		return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);

		return ResponseEntity.noContent().build();
	}

}
