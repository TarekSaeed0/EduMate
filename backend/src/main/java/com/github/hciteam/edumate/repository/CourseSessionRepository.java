package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.CourseSession;

public interface CourseSessionRepository
		extends JpaRepository<CourseSession, Long> {
	List<CourseSession> findByOfferingRegistrationsStudentId(Long studentId);

	Optional<CourseSession> findByOfferingIdAndSlotId(Long offeringId,
			Long slotId);

	boolean existsByOfferingIdAndSlotId(Long offeringId, Long slotId);
}
