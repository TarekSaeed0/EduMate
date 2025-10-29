package com.github.hciteam.edumate.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FAQDTO {
	private Long id;
	private String question;
	private String answer;
	private Set<String> categories;
}
