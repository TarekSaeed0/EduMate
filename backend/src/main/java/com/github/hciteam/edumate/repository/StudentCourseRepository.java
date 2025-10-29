package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.StudentCourse;

public interface StudentCourseRepository
		extends JpaRepository<StudentCourse, Long> {
	List<StudentCourse> findByStudentId(Long studentId);

	List<StudentCourse> findBySemesterCourseId(Long semesterCourseId);

	Optional<StudentCourse> findByStudentIdAndSemesterCourseId(Long studentId,
			Long semesterCourseId);
}
