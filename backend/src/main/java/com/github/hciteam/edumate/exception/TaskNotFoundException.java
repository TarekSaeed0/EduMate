package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends ApiException {
	public TaskNotFoundException() {
		super("TASK_NOT_FOUND", "Task with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
