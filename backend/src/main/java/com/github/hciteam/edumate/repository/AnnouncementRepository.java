package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.Announcement;

public interface AnnouncementRepository
		extends JpaRepository<Announcement, Long> {
	List<Announcement> findByScopeTypeAndScopeIdOrderByCreatedAtDesc(
			String scopeType, Long scopeId);
}
