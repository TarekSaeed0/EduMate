package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.CourseSessionType;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSessionDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private CourseOfferingDTO offering;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private TimeSlotDTO slot;

	private String location;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private CourseSessionType type;
}
