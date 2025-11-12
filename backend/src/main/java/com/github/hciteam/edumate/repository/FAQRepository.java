package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.entity.FAQ;

public interface FAQRepository
		extends JpaRepository<FAQ, Long>, JpaSpecificationExecutor<FAQ> {
}
