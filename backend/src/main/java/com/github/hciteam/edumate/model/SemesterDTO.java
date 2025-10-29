package com.github.hciteam.edumate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDTO {
	private Long id;
	private Term term;
	private Long year;
	private String startDate;
	private String endDate;
}
