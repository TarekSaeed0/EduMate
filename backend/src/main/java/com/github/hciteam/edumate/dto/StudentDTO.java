package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.Gender;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private UniversityDTO university;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String name;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Gender gender;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String email;

	@Null(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Long userId;
}
