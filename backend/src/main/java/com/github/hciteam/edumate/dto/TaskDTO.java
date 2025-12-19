package com.github.hciteam.edumate.dto;

import java.time.LocalDateTime;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private CourseOfferingDTO offering;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String title;

	private String requirements;

	private String submissionUrl;

	private LocalDateTime dueDate;

	private String notes;
};
