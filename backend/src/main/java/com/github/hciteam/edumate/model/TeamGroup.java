package com.github.hciteam.edumate.model;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "team_groups")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "offering_id", nullable = false)
	private CourseOffering offering;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer minimumMemberCount;

	@Column(nullable = false)
	private Integer maximumMemberCount;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Team> teams;
}
