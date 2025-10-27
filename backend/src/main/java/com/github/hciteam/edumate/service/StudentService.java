package com.github.hciteam.edumate.service;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.mapper.StudentMapper;
import com.github.hciteam.edumate.model.StudentDTO;
import com.github.hciteam.edumate.repository.StudentRepository;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final StudentMapper studentMapper;

	public StudentService(StudentRepository studentRepository,
			StudentMapper studentMapper) {
		this.studentRepository = studentRepository;
		this.studentMapper = studentMapper;
	}

	public StudentDTO me(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Student student = studentRepository.findByUserId(user.getId())
				.orElseThrow(() -> new RuntimeException("User is not a student"));
		return studentMapper.toDTO(student);
	}
}
