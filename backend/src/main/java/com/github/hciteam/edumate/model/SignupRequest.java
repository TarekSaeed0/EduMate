package com.github.hciteam.edumate.model;

import com.github.hciteam.edumate.validation.Gmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@NotBlank
	private String name;
	@Email
	@Gmail
	private String email;
	@Email
	private String universityEmail;
	@NotBlank
	private String studentId;
	@NotBlank
	private String password;
}
