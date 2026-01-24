package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.Course;

public class CourseSpecifications {
	public static Specification<Course> ofCode(String code) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("code"), code);
	}

	public static Specification<Course> ofName(String name) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.like(root.get("name"), "%" + name + "%");
	}
}
