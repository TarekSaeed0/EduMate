package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.TeamJoinRequest;
import com.github.hciteam.edumate.model.TeamJoinStatus;

public class TeamJoinRequestSpecifications {
	public static Specification<TeamJoinRequest> ofTeam(Long teamId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("team").get("id"), teamId);
	}

	public static Specification<TeamJoinRequest> ofStudent(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("student").get("id"), studentId);
	}

	public static Specification<TeamJoinRequest> ofStatus(TeamJoinStatus status) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("status"), status);
	}
}
