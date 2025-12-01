package com.github.hciteam.edumate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinInviteDTO {
	private Long id;
	private TeamDTO team;
	private Long studentId;
}
