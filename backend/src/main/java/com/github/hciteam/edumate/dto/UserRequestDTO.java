package com.github.hciteam.edumate.dto;

import java.util.Set;
import com.github.hciteam.edumate.validation.Gmail;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	@Email
	@Gmail
	private String email;

	@NotBlank(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String password;

	private Set<String> roles;

	private StudentDTO student;
}
