package com.github.hciteam.edumate.model;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "universities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class University implements AnnouncementScope {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToOne
	@JoinColumn(name = "current_semester_id")
	private Semester currentSemester;

	@OneToMany(mappedBy = "university", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Course> courses;

	@OneToMany(mappedBy = "university", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Student> students;

	public University(String name, Semester currentSemester) {
		this.name = name;
		this.currentSemester = currentSemester;
	}

	@Override
	public String getScopeType() {
		return "UNIVERSITY";
	}

	@Override
	public Long getScopeId() {
		return this.id;
	}
}
