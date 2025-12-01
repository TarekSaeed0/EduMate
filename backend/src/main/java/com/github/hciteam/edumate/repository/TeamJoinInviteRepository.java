package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.TeamJoinInvite;

public interface TeamJoinInviteRepository
		extends JpaRepository<TeamJoinInvite, Long> {
}
