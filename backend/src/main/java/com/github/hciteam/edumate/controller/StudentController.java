package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.model.StudentCourseDTO;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.model.StudentTaskDTO;
import com.github.hciteam.edumate.model.StudentTaskStatus;
import com.github.hciteam.edumate.service.StudentService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	StudentService studentService;

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
			@PathVariable Long studentId,
			@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) StudentTaskStatus status) {
		return ResponseEntity
				.ok(studentService.getStudentTasks(studentId, courseId, status));
	}

	@GetMapping("/{studentId}/tasks/{taskId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentTaskDTO> getStudentTask(
			@PathVariable Long studentId, @PathVariable Long taskId) {
		return ResponseEntity.ok(studentService.getStudentTask(studentId, taskId));
	}

	@GetMapping("/{studentId}/courses")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<List<StudentCourseDTO>> getStudentCourses(
			@PathVariable Long studentId) {
		return ResponseEntity.ok(studentService.getStudentCourses(studentId));
	}

	@PostMapping("/{studentId}/courses")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentCourseDTO> createStudentCourse(
			@PathVariable Long studentId,
			@RequestBody StudentCourseDTO studentCourseDTO) {
		StudentCourseDTO createdStudentCourseDTO =
				studentService.createStudentCourse(studentId, studentCourseDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{studentCourseId}")
				.buildAndExpand(studentCourseDTO.getSemesterCourse().getId()).toUri();

		return ResponseEntity.created(location).body(createdStudentCourseDTO);
	}

	@GetMapping("/{studentId}/courses/{semesterCourseId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentCourseDTO> getStudentCourse(
			@PathVariable Long studentId, @PathVariable Long semesterCourseId) {
		return ResponseEntity
				.ok(studentService.getStudentCourse(studentId, semesterCourseId));
	}

	@PutMapping("/{studentId}/courses/{semesterCourseId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<StudentCourseDTO> updateStudentCourse(
			@PathVariable Long studentId, @PathVariable Long semesterCourseId,
			@RequestBody StudentCourseDTO studentCourseDTO) {
		StudentCourseDTO updatedStudentCourseDTO = studentService
				.updateStudentCourse(studentId, semesterCourseId, studentCourseDTO);

		return ResponseEntity.ok(updatedStudentCourseDTO);
	}

	@DeleteMapping("/{studentId}/courses/{semesterCourseId}")
	@PreAuthorize("@authorizationService.isStudentSelf(#studentId) or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteStudentCourse(@PathVariable Long studentId,
			@PathVariable Long semesterCourseId) {
		studentService.deleteStudentCourse(studentId, semesterCourseId);

		return ResponseEntity.noContent().build();
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
			Authentication authentication,
			@RequestParam(required = false) Long courseId,
			@RequestParam(required = false) StudentTaskStatus status) {
		return ResponseEntity.ok(studentService
				.getCurrentStudentTasks(authentication, courseId, status));
	}

	@GetMapping("/me/tasks/{taskId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentTaskDTO> getCurrentStudentTask(
			Authentication authentication, @PathVariable Long taskId) {
		return ResponseEntity
				.ok(studentService.getCurrentStudentTask(authentication, taskId));
	}

	@GetMapping("/me/courses")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<List<StudentCourseDTO>> getCurrentStudentCourses(
			Authentication authentication) {
		return ResponseEntity
				.ok(studentService.getCurrentStudentCourses(authentication));
	}

	@PostMapping("/me/courses")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentCourseDTO> createCurrentStudentCourse(
			Authentication authentication,
			@RequestBody StudentCourseDTO studentCourseDTO) {
		StudentCourseDTO createdStudentCourseDTO = studentService
				.createCurrentStudentCourse(authentication, studentCourseDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{studentCourseId}")
				.buildAndExpand(studentCourseDTO.getSemesterCourse().getId()).toUri();

		return ResponseEntity.created(location).body(createdStudentCourseDTO);
	}

	@GetMapping("/me/courses/{semesterCourseId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentCourseDTO> getCurrentStudentCourse(
			Authentication authentication, @PathVariable Long semesterCourseId) {
		return ResponseEntity.ok(studentService
				.getCurrentStudentCourse(authentication, semesterCourseId));
	}

	@PutMapping("/me/courses/{semesterCourseId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentCourseDTO> updateCurrentStudentCourse(
			Authentication authentication, @PathVariable Long semesterCourseId,
			@RequestBody StudentCourseDTO studentCourseDTO) {
		StudentCourseDTO updatedStudentCourseDTO =
				studentService.updateCurrentStudentCourse(authentication,
						semesterCourseId, studentCourseDTO);

		return ResponseEntity.ok(updatedStudentCourseDTO);
	}

	@DeleteMapping("/me/courses/{semesterCourseId}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Void> deleteCurrentStudentCourse(
			Authentication authentication, @PathVariable Long semesterCourseId) {
		studentService.deleteCurrentStudentCourse(authentication, semesterCourseId);

		return ResponseEntity.noContent().build();
	}
}
