package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>,
		JpaSpecificationExecutor<Course>, AnnouncementScopeRepository {
	Optional<Course> findByCode(String code);

	boolean existsByCode(String code);

	@Override
	default String getScopeType() {
		return "COURSE";
	}

	@Override
	default Optional<AnnouncementScope> findScopeById(Long id) {
		return findById(id).map(scope -> (AnnouncementScope) scope);
	}

	@Override
	default boolean existsScopeById(Long id) {
		return existsById(id);
	}
}
