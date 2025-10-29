package com.github.hciteam.edumate.entity;

import java.io.Serializable;
import java.util.Set;
import com.github.hciteam.edumate.model.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
	@Id
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false, unique = true)
	private String email;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<StudentCourse> studentCourses;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<StudentTask> studentTasks;
}
