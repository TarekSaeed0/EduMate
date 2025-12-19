package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.model.TeamJoinStatus;

public class TeamJoinInviteSpecifications {
	public static Specification<TeamJoinInvite> ofTeam(Long teamId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("team").get("id"), teamId);
	}

	public static Specification<TeamJoinInvite> ofStudent(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("student").get("id"), studentId);
	}

	public static Specification<TeamJoinInvite> ofStatus(TeamJoinStatus status) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("status"), status);
	}
}
