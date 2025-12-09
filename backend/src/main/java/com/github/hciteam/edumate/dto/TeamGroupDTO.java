package com.github.hciteam.edumate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamGroupDTO {
	private Long id;
	private CourseOfferingDTO offering;
	private String name;
	private Integer minimumMemberCount;
	private Integer maximumMemberCount;
}
