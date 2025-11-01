package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.github.hciteam.edumate.entity.StudentTask;
import com.github.hciteam.edumate.key.StudentTaskKey;

public interface StudentTaskRepository
		extends JpaRepository<StudentTask, StudentTaskKey>,
		JpaSpecificationExecutor<StudentTask> {
	List<StudentTask> findByStudentId(Long studentId);

	List<StudentTask> findByTaskId(Long taskId);
}
