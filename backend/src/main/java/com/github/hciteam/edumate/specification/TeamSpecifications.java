package com.github.hciteam.edumate.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.Team;

public class TeamSpecifications {
	public static Specification<Team> ofGroup(Long groupId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("group").get("id"), groupId);
	}

	public static Specification<Team> ofLeader(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("leader").get("id"), studentId);
	}

	public static Specification<Team> isMember(Long studentId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(studentId,
				root.get("members"));
	}

	public static Specification<Team> isIncomplete() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(
				criteriaBuilder.size(root.get("members")),
				root.get("group").get("minimumMemberCount"));
	}

	public static Specification<Team> isSufficient() {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.greaterThanOrEqualTo(criteriaBuilder.size(root.get("members")),
						root.get("group").get("minimumMemberCount"));
	}

	public static Specification<Team> isComplete() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
				criteriaBuilder.size(root.get("members")),
				root.get("group").get("maximumMemberCount"));
	}
}
