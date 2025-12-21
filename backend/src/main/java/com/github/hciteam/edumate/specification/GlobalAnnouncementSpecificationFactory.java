package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.model.Announcement;

@Component
public class GlobalAnnouncementSpecificationFactory
		implements AnnouncementSpecificationFactory {
	@Override
	public String getScopeType() {
		return "GLOBAL";
	}

	@Override
	public Specification<Announcement> ofStudent(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("scopeType"), "GLOBAL");
	}
}
