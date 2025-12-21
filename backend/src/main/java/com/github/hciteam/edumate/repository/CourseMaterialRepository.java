package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.model.CourseMaterial;

public interface CourseMaterialRepository
		extends JpaRepository<CourseMaterial, Long>,
		JpaSpecificationExecutor<CourseMaterial> {
}
