package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.exception.StudentNotFound;
import com.github.hciteam.edumate.mapper.StudentMapper;
import com.github.hciteam.edumate.mapper.StudentTaskMapper;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.model.StudentTaskDTO;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.StudentTaskRepository;

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

	public StudentDTO getStudent(Long id) {
		Student student =
				studentRepository.findById(id).orElseThrow(() -> new StudentNotFound());
		return studentMapper.toDTO(student);
	}

	public List<StudentTaskDTO> getStudentTasks(Long id) {
		return studentTaskRepository.findByStudentId(id).stream()
				.map(task -> studentTaskMapper.toDTO(task)).toList();
	}

	public StudentDTO getCurrentStudent(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFound());
		return studentMapper.toDTO(student);
	}

	public List<StudentTaskDTO> getCurrentStudentTasks(
			Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new StudentNotFound());
		return getStudentTasks(student.getId());
	}
}
