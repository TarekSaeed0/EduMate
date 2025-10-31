package com.github.hciteam.edumate.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;
import com.github.hciteam.edumate.repository.StudentRepository;

@Service("authorizationService")
public class AuthorizationService {
	StudentRepository studentRepository;

	public AuthorizationService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public boolean isStudentSelf(Long id) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Optional<Student> student = studentRepository.findByUserId(user.getId());
		return student.isPresent() && student.get().getId().equals(id);
	}
}
