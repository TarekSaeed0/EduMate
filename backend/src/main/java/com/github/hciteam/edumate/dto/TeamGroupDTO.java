package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamGroupDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private CourseOfferingDTO offering;
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String name;
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Integer minimumMemberCount;
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Integer maximumMemberCount;
}
