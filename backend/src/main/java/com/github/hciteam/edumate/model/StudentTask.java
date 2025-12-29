
package com.github.hciteam.edumate.model;

import java.time.LocalDateTime;
import com.github.hciteam.edumate.key.StudentTaskKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentTask {
	@EmbeddedId
	private StudentTaskKey id;

	@MapsId("studentId")
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@MapsId("taskId")
	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	private LocalDateTime submittedAt;

	public StudentTask(Student student, Task task) {
		this.student = student;
		this.task = task;
		this.id = new StudentTaskKey(student.getId(), task.getId());
	}
}
