package com.github.hciteam.edumate.dto;

import java.time.LocalDate;
import com.github.hciteam.edumate.model.Term;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDTO {
	private Long id;
	private Term term;
	private Integer year;
	private LocalDate startDate;
	private LocalDate endDate;
}
