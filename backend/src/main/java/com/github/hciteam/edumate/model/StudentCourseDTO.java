package com.github.hciteam.edumate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDTO {
	private Long studentId;
	private SemesterCourseDTO semesterCourse;
	private StudentCourseStatus status;
}
