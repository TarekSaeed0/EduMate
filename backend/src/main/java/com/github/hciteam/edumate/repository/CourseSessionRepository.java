package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.CourseSession;

public interface CourseSessionRepository
		extends JpaRepository<CourseSession, Long> {
	List<CourseSession> findByOfferingRegistrationsStudentId(Long studentId);
}
