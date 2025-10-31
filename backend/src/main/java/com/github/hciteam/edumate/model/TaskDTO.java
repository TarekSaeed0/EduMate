package com.github.hciteam.edumate.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
	private Long id;
	private SemesterCourseDTO semesterCourse;
	private String title;
	private String requirements;
	private String submissionUrl;
	private LocalDateTime dueDate;
	private String notes;
};
