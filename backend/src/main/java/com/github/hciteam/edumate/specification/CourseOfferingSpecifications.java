package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.CourseOffering;

public class CourseOfferingSpecifications {
	public static Specification<CourseOffering> ofSemester(Long semesterId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("offering").get("semester").get("id"), semesterId);
	}

	public static Specification<CourseOffering> ofCourse(Long courseId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("offering").get("course").get("id"), courseId);
	}
}
