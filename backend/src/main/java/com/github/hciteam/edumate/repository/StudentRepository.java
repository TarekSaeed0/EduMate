package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUserId(Long userId);
}
