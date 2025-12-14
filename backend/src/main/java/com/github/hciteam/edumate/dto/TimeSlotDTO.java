package com.github.hciteam.edumate.dto;

import com.github.hciteam.edumate.model.WeekDay;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private TimePeriodDTO period;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private WeekDay weekDay;
}
