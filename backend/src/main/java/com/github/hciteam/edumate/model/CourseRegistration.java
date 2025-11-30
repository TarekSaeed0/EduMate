package com.github.hciteam.edumate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_registrations",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"offering_id", "student_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "offering_id", nullable = false)
	private CourseOffering offering;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseRegistrationStatus status;
}
