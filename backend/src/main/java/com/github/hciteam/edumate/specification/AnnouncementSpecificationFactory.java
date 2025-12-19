package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.Announcement;

public interface AnnouncementSpecificationFactory {
	String getScopeType();

	Specification<Announcement> ofStudent(Long studentId);
}
