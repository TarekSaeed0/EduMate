package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.TeamJoinStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinRequestDTO {
	private Long id;
	private TeamDTO team;
	private StudentDTO student;
	private TeamJoinStatus status;
}
