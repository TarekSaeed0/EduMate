package com.github.hciteam.edumate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
	private Long id;
	private String code;
	private String name;
	private Integer credits;
}
