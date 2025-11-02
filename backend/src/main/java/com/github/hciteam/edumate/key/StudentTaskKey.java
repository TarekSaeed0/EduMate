package com.github.hciteam.edumate.key;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskKey implements Serializable {
	private Long studentId;
	private Long taskId;
}
