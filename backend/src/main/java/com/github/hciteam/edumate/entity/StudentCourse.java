package com.github.hciteam.edumate.entity;

import com.github.hciteam.edumate.key.StudentCourseKey;
import com.github.hciteam.edumate.model.StudentCourseStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
	@EmbeddedId
	private StudentCourseKey id;

	@ManyToOne
	@MapsId("studentId")
	@JoinColumn(name = "student_id", nullable = false)
	Student student;

	@ManyToOne
	@MapsId("semesterCourseId")
	@JoinColumn(name = "semester_course_id", nullable = false)
	SemesterCourse semesterCourse;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	StudentCourseStatus status;
}
