package com.github.hciteam.edumate.dto;

import java.util.List;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private TeamGroupDTO group;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private StudentDTO leader;

	@Null(groups = {ValidationGroups.Create.class})
	private List<StudentDTO> members;
}
