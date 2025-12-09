package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.dto.StudentDTO;
import com.github.hciteam.edumate.dto.StudentTaskDTO;
import com.github.hciteam.edumate.model.StudentTaskStatus;
import com.github.hciteam.edumate.service.StudentService;
import com.github.hciteam.edumate.validation.ValidationGroups;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/{studentId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long studentId) {
		return ResponseEntity.ok(studentService.getStudent(studentId));
	}

	@GetMapping("/{studentId}/tasks")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentTaskDTO>> getStudentTasks(
			@PathVariable Long studentId, @RequestParam(required = false) Long taskId,
			@RequestParam(required = false) Long semesterId,
			@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) StudentTaskStatus status) {
		return ResponseEntity.ok(studentService.getStudentTasks(studentId, taskId,
				semesterId, courseId, status));
	}

	@GetMapping("/{studentId}/tasks/{taskId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentTaskDTO> getStudentTask(
			@PathVariable Long studentId, @PathVariable Long taskId) {
		return ResponseEntity.ok(studentService.getStudentTask(studentId, taskId));
	}

	@PutMapping("/{studentId}/tasks/{taskId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentTaskDTO> updateStudentTask(
			@PathVariable Long studentId, @PathVariable Long taskId,
			@Validated(ValidationGroups.Update.class) @RequestBody StudentTaskDTO studentTaskDTO) {
		return ResponseEntity.ok(
				studentService.updateStudentTask(studentId, taskId, studentTaskDTO));
	}

}
