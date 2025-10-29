package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
	List<FAQ> findByCategoriesName(String categoryName);
}
