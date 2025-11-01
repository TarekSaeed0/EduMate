package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.StudentCourse;
import com.github.hciteam.edumate.key.StudentCourseKey;

public interface StudentCourseRepository
		extends JpaRepository<StudentCourse, StudentCourseKey> {
	List<StudentCourse> findByStudentId(Long studentId);

	List<StudentCourse> findBySemesterCourseId(Long semesterCourseId);
}
