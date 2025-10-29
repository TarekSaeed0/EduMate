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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "semester_courses",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"semester_id", "course_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "semester_id", nullable = false)
	private Semester semester;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@OneToMany(mappedBy = "semesterCourse", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<StudentCourse> studentCourses;

	@OneToMany(mappedBy = "semesterCourse", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Task> tasks;
}
