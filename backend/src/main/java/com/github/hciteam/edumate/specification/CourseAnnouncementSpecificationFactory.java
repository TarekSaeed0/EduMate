package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.Announcement;
import com.github.hciteam.edumate.model.Course;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.CourseRegistration;
import com.github.hciteam.edumate.model.Student;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

class CourseAnnouncementSpecificationFactory
		implements AnnouncementSpecificationFactory {
	@Override
	public String getScopeType() {
		return "COURSE";
	}

	@Override
	public Specification<Announcement> ofUser(Long userId) {
		return (root, query, criteriaBuilder) -> {
			Subquery<Long> subquery = query.subquery(Long.class);
			Root<Course> courses = subquery.from(Course.class);

			Join<Course, CourseOffering> offerings = courses.join("offerings");
			Join<CourseOffering, CourseRegistration> registrations =
					offerings.join("registrations");
			Join<CourseRegistration, Student> student = registrations.join("student");

			subquery.select(courses.get("id"))
					.where(criteriaBuilder.equal(student.get("user").get("id"), userId));

			return criteriaBuilder.and(
					criteriaBuilder.equal(root.get("scopeType"), getScopeType()),
					criteriaBuilder.in(root.get("scopeId")).value(subquery));
		};

	}
}
