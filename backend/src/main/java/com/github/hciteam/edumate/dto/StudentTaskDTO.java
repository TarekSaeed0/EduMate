package com.github.hciteam.edumate.dto;

import java.time.LocalDateTime;
import com.github.hciteam.edumate.model.StudentTaskStatus;
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

	private StudentTaskStatus status;
};
