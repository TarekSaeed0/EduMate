package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.model.CourseOffering;

public interface CourseOfferingRepository
		extends JpaRepository<CourseOffering, Long>,
		JpaSpecificationExecutor<CourseOffering> {
	List<CourseOffering> findBySemesterId(Long semesterId);

	List<CourseOffering> findByCourseId(Long courseId);

	Optional<CourseOffering> findBySemesterIdAndCourseId(Long semesterId,
			Long courseId);

	boolean existsBySemesterIdAndCourseId(Long semesterId, Long courseId);
}
