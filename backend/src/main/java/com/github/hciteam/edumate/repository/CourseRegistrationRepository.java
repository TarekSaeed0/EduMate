package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.model.CourseRegistration;

public interface CourseRegistrationRepository
		extends JpaRepository<CourseRegistration, Long>,
		JpaSpecificationExecutor<CourseRegistration> {
	List<CourseRegistration> findByOfferingId(Long offeringId);

	List<CourseRegistration> findByStudentId(Long studentId);

	Optional<CourseRegistration> findByOfferingIdAndStudentId(Long offeringId,
			Long studentId);

	boolean existsByOfferingIdAndStudentId(Long offeringId, Long studentId);
}
