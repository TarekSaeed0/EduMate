package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.CourseRegistration;

public interface CourseRegistrationRepository
		extends JpaRepository<CourseRegistration, Long> {
	List<CourseRegistration> findByStudentId(Long studentId);

	List<CourseRegistration> findByOfferingId(Long offeringId);

	Optional<CourseRegistration> findByStudentIdAndOfferingId(Long studentId,
			Long offeringId);
}
