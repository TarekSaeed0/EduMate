package com.github.hciteam.edumate.dto;

import java.time.LocalTime;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimePeriodDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private LocalTime startTime;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private LocalTime endTime;
}
