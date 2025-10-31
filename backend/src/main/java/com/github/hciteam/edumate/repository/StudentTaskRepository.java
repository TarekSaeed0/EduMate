package com.github.hciteam.edumate.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.entity.StudentTask;

public interface StudentTaskRepository extends JpaRepository<StudentTask, Long>,
		JpaSpecificationExecutor<StudentTask> {
	List<StudentTask> findByStudentId(Long studentId);

	List<StudentTask> findByTaskId(Long taskId);

	Optional<StudentTask> findByStudentIdAndTaskId(Long studentId, Long taskId);
}
