package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.Gender;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {
	@NotNull(groups = {ValidationGroups.Create.class})
	@Null(groups = {ValidationGroups.Update.class})
	private Long id;

	@NotBlank(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String name;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Gender gender;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	@Email
	private String email;
}
