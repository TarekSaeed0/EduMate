package com.github.hciteam.edumate.entity;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_offerings",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"course_id", "semester_id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOffering {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@ManyToOne
	@JoinColumn(name = "semester_id", nullable = false)
	private Semester semester;

	@OneToMany(mappedBy = "offering", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<CourseRegistration> registrations;

	@OneToMany(mappedBy = "offering", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Task> tasks;
}
