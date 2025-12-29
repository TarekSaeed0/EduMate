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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course implements AnnouncementScope {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "university_id", nullable = false)
	private University university;

	@Column(nullable = false, unique = true)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer credits;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<CourseOffering> offerings;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<CourseMaterial> materials;

	public Course(University university, String code, String name,
			Integer credits) {
		this.university = university;
		this.code = code;
		this.name = name;
		this.credits = credits;
	}

	@Override
	public String getScopeType() {
		return "COURSE";
	}

	@Override
	public Long getScopeId() {
		return this.id;
	}
}
