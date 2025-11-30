package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	Optional<Team> findByGroupIdAndMembersId(Long groupId, Long studentId);

	boolean existsByGroupIdAndMembersId(Long groupId, Long studentId);
}
