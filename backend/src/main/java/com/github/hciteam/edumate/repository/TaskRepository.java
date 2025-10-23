package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
