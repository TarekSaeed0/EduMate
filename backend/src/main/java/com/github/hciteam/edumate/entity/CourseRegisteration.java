package com.github.hciteam.edumate.entity;

import java.time.LocalDateTime;
import com.github.hciteam.edumate.model.CourseRegisterationStatus;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegisteration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	Student student;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	Course course;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	CourseRegisterationStatus status;

	@Column(nullable = false)
	LocalDateTime registeredAt;

	LocalDateTime completedAt;
}
