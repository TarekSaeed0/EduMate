package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.model.Announcement;

public interface AnnouncementRepository extends
		JpaRepository<Announcement, Long>, JpaSpecificationExecutor<Announcement> {
}
