package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.SemesterCourse;

public interface SemesterCourseRepository
		extends JpaRepository<SemesterCourse, Long> {
	List<SemesterCourse> findBySemesterId(Long semesterId);

	List<SemesterCourse> findByCourseId(Long courseId);

	Optional<SemesterCourse> findBySemesterIdAndCourseId(Long semesterId,
			Long courseId);

	boolean existsBySemesterIdAndCourseId(Long semesterId, Long courseId);
}
