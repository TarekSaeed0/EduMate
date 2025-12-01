package com.github.hciteam.edumate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
	private Long id;
	private TeamGroupDTO group;
	private Long leaderId;
	private List<Long> memberIds;
}
