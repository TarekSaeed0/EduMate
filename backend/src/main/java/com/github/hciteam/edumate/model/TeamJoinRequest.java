package com.github.hciteam.edumate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "team_join_requests")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamJoinRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable = false)
	private Team team;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@JoinColumn(nullable = false)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private TeamJoinStatus status = TeamJoinStatus.PENDING;
}
