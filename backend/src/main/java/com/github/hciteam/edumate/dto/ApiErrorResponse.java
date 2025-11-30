package com.github.hciteam.edumate.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
	private String type;
	private String message;
	private int status;
	private String path;
	private Instant timestamp;
}
