package com.github.hciteam.edumate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfferingDTO {
	private Long id;
	private Long semesterId;
	private CourseDTO course;
}
