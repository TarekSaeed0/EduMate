package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.CourseOffering;

public interface CourseOfferingRepository
		extends JpaRepository<CourseOffering, Long> {
	List<CourseOffering> findByCourseId(Long courseId);

	List<CourseOffering> findBySemesterId(Long semesterId);

	Optional<CourseOffering> findByCourseIdAndSemesterId(Long courseId,
			Long semesterId);
}
