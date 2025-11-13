package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.entity.FAQ;

public interface FAQRepository
		extends JpaRepository<FAQ, Long>, JpaSpecificationExecutor<FAQ> {
	List<FAQ> findByQuestionContainingIgnoreCase(String keyword);

	List<FAQ> findByAnswerContainingIgnoreCase(String keyword);
}
