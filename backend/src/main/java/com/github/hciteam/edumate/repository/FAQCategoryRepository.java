package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.FAQCategory;

public interface FAQCategoryRepository extends JpaRepository<FAQCategory, Long> {
	Optional<FAQCategory> findByName(String name);

	boolean existsByName(String name);
}
