package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
