package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.SemesterCourse;

public interface SemesterCourseRepository
		extends JpaRepository<SemesterCourse, Long> {
	List<SemesterCourse> findByCourseId(Long courseId);

	List<SemesterCourse> findBySemesterId(Long semesterId);

	Optional<SemesterCourse> findByCourseIdAndSemesterId(Long courseId,
			Long semesterId);
}
