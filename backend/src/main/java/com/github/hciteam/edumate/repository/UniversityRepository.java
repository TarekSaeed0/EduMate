package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.model.University;

public interface UniversityRepository
		extends JpaRepository<University, Long>, AnnouncementScopeRepository {
	Optional<University> findByName(String name);

	boolean existsByName(String name);

	@Override
	default String getScopeType() {
		return "UNIVERSITY";
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
