package com.github.hciteam.edumate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findBySemesterCourseId(Long semesterCourseId);
}
