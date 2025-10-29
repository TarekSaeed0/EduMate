package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.exception.StudentNotFound;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.model.StudentTaskDTO;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.service.StudentService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	StudentService studentService;
	StudentRepository studentRepository;

	public StudentController(StudentService studentService,
			StudentRepository studentRepository) {
		this.studentService = studentService;
		this.studentRepository = studentRepository;
	}

	public boolean isSelf(Long id) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Optional<Student> student = studentRepository.findByUserId(user.getId());
		return student.isPresent() && student.get().getId().equals(id);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isSelf(#id) or hasRole('ADMIN')")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
		return ResponseEntity.ok(studentService.getStudent(id));
	}

	@GetMapping("/{id}/tasks")
	@PreAuthorize("isSelf(#id) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentTaskDTO>> getStudentTasks(
			@PathVariable Long id) {
		return ResponseEntity.ok(studentService.getStudentTasks(id));
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentDTO> getCurrentStudent(
			Authentication authentication) {
		return ResponseEntity.ok(studentService.getCurrentStudent(authentication));
	}

	@GetMapping("/me/tasks")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<List<StudentTaskDTO>> getCurrentStudentTasks(
			Authentication authentication) {
		return ResponseEntity
				.ok(studentService.getCurrentStudentTasks(authentication));
	}
}
