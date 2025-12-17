package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.model.Course;

public interface CourseRepository
		extends JpaRepository<Course, Long>, AnnouncementScopeRepository {
	Optional<Course> findByCode(String code);

	boolean existsByCode(String code);

	default String getType() {
		return "Course";
	}

	default Optional<AnnouncementScope> findScopeById(Long id) {
		return findById(id).map(scope -> (AnnouncementScope) scope);
	}

	default boolean existsScopeById(Long id) {
		return existsById(id);
	}
}
