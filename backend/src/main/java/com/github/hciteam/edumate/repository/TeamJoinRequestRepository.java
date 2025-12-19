package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.model.TeamJoinRequest;

public interface TeamJoinRequestRepository
		extends JpaRepository<TeamJoinRequest, Long>,
		JpaSpecificationExecutor<TeamJoinRequest> {
}
