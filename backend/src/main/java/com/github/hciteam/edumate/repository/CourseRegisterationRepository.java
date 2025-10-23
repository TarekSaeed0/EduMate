package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.CourseRegisteration;

public interface CourseRegisterationRepository
		extends JpaRepository<CourseRegisteration, Long> {
	List<CourseRegisteration> findByStudentId(Long studentId);

	List<CourseRegisteration> findByOfferingId(Long offeringId);

	Optional<CourseRegisteration> findByStudentIdAndOfferingId(Long studentId,
			Long offeringId);
}
