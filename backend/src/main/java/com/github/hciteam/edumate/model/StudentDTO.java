package com.github.hciteam.edumate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
	private Long id;
	private String name;
	private Gender gender;
	private String email;
	private Long userId;
}
