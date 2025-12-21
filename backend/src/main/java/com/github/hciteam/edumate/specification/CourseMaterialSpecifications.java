package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.CourseMaterial;

public class CourseMaterialSpecifications {
	public static Specification<CourseMaterial> ofCourse(Long courseId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("course").get("id"), courseId);
	}
}
