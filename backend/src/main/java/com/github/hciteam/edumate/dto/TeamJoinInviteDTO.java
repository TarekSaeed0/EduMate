package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinInviteDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private TeamDTO team;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private StudentDTO student;

	@Null(groups = {ValidationGroups.Create.class})
	private TeamJoinStatus status;
}
