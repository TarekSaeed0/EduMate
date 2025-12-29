package com.github.hciteam.edumate.model;

import java.io.Serializable;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "university_id", nullable = false)
	private University university;

	@Column(nullable = false)
	private String name;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<CourseRegistration> registrations;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<StudentTask> studentTasks;
}
