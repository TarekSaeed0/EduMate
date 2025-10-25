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
public class SigninRequest {
	@NotNull
	@Email
	@Gmail
	private String email;

	@NotBlank
	private String password;
}
