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
public class AnnouncementDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String scopeType;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private Long scopeId;

	@Null(groups = {ValidationGroups.Create.class})
	private AnnouncementScopeDTO scope;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String title;

	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String content;

	@Null(groups = {ValidationGroups.Create.class})
	private LocalDateTime createdAt;
}
