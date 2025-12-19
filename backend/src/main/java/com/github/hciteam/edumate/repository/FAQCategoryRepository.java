package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.github.hciteam.edumate.model.FAQCategory;

public interface FAQCategoryRepository
		extends JpaRepository<FAQCategory, Long> {
	Optional<FAQCategory> findByName(String name);

	boolean existsByName(String name);

	@Query("""
				SELECT c FROM FAQCategory c
				WHERE c NOT IN (
					SELECT DISTINCT fc
					FROM FAQ f
					JOIN f.categories fc
				)
			""")
	List<FAQCategory> findUnusedCategories();
}
