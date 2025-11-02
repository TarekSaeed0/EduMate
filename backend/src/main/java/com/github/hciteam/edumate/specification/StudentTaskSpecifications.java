package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.entity.StudentTask;

public class StudentTaskSpecifications {
	public static Specification<StudentTask> ofStudent(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("student").get("id"), studentId);
	}

	public static Specification<StudentTask> ofSemester(Long semesterId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
				root.get("task").get("semesterCourse").get("semester").get("id"),
				semesterId);
	}

	public static Specification<StudentTask> ofCourse(Long courseId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
				root.get("task").get("semesterCourse").get("course").get("id"),
				courseId);
	}

	public static Specification<StudentTask> isUpcoming() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.and(
				criteriaBuilder.isNull(root.get("submittedAt")),
				criteriaBuilder.lessThan(criteriaBuilder.currentTimestamp(),
						root.get("task").get("dueDate")));
	}

	public static Specification<StudentTask> isCompleted() {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.isNotNull(root.get("submittedAt"));
	}

	public static Specification<StudentTask> isOverdue() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.and(
				criteriaBuilder.isNull(root.get("submittedAt")),
				criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.currentTimestamp(),
						root.get("task").get("dueDate")));
	}
}
