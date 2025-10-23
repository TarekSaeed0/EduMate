package com.github.hciteam.edumate.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
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
	@JsonBackReference
	private User user;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<CourseRegisteration> registerations;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<StudentTask> tasks;
}
