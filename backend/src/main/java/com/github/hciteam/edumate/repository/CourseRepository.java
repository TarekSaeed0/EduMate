package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findByCode(String code);

	boolean existsByCode(String code);
}
