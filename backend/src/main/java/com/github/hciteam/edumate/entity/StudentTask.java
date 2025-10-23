
package com.github.hciteam.edumate.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_tasks",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"student_id", "task_id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	@Column(name = "submitted_at")
	private LocalDateTime submittedAt;
}
