package com.github.hciteam.edumate.model;

import com.github.hciteam.edumate.validation.Gmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@NotNull
	private Long id;

	@NotBlank
	private String name;

	@NotNull
	private Gender gender;

	@NotNull
	@Email
	@Gmail
	private String email;

	@NotNull
	@Email
	private String universityEmail;

	@NotBlank
	private String password;
}
