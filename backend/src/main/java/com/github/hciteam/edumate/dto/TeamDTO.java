package com.github.hciteam.edumate.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
	private Long id;
	private TeamGroupDTO group;
	private StudentDTO leader;
	private List<StudentDTO> members;
}
