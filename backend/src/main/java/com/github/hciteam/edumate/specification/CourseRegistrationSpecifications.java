package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.CourseRegistrationStatus;

public class CourseRegistrationSpecifications {
	public static Specification<CourseRegistration> ofOffering(Long offeringId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("offering").get("id"), offeringId);
	}

	public static Specification<CourseRegistration> ofSemester(Long semesterId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("offering").get("semester").get("id"), semesterId);
	}

	public static Specification<CourseRegistration> ofCourse(Long courseId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("offering").get("course").get("id"), courseId);
	}

	public static Specification<CourseRegistration> ofStudent(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("student").get("id"), studentId);
	}

	public static Specification<CourseRegistration> ofStatus(
			CourseRegistrationStatus status) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("status"), status);
	}
}
