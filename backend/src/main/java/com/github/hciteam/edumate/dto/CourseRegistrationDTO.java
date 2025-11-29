package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.CourseRegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationDTO {
	private Long id;
	private CourseOfferingDTO offering;
	private Long studentId;
	private CourseRegistrationStatus status;
}
