package com.github.hciteam.edumate.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskDTO {
	private Long studentId;
	private TaskDTO task;
	private LocalDateTime submittedAt;
};
