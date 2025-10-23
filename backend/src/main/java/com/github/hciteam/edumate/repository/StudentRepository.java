package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.Student;
import com.github.hciteam.edumate.entity.User;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUser(User user);
}
