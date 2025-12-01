package com.github.hciteam.edumate.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDTO {
	private Long id;
	private String email;
	private String password;
	private Set<String> roles;
	private StudentDTO student;
}
