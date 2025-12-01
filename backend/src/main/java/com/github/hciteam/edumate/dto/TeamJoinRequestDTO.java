package com.github.hciteam.edumate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinRequestDTO {
	private Long id;
	private Long teamId;
	private StudentDTO student;
}
