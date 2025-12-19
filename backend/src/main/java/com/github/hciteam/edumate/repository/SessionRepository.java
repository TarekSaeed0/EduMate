package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
	List<Session> findByOfferingRegistrationsStudentId(Long studentId);
}
