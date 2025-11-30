package com.github.hciteam.edumate.service;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.User;
import com.github.hciteam.edumate.repository.CourseRegistrationRepository;
import com.github.hciteam.edumate.repository.StudentRepository;

@Service("authorizationService")
public class AuthorizationService {
	StudentRepository studentRepository;
	CourseRegistrationRepository registrationRepository;

	public AuthorizationService(StudentRepository studentRepository,
			CourseRegistrationRepository registrationRepository) {
		this.studentRepository = studentRepository;
		this.registrationRepository = registrationRepository;
	}

	public boolean isStudentSelf(Long studentId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		return student.get().getId().equals(studentId);
	}

	public boolean isRegistrationOwner(Long registrationId) {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();

		Optional<Student> student = studentRepository.findByUserId(user.getId());
		if (student.isEmpty()) {
			return false;
		}

		Optional<CourseRegistration> registration =
				registrationRepository.findById(registrationId);
		if (registration.isEmpty()) {
			return false;
		}

		return registration.get().getStudent().getId()
				.equals(student.get().getId());
	}
}
