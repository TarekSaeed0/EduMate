package com.github.hciteam.edumate.dto;

import java.time.LocalDate;
import com.github.hciteam.edumate.model.Term;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Term term;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Integer year;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private LocalDate startDate;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private LocalDate endDate;
}
