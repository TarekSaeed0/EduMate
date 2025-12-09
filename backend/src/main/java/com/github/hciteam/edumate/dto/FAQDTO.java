package com.github.hciteam.edumate.dto;

import java.util.Set;
import com.github.hciteam.edumate.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FAQDTO {
	@Null(groups = {ValidationGroups.Create.class})
	private Long id;
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String question;
	@NotNull(
			groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
	private String answer;
	private Set<String> categories;
}
