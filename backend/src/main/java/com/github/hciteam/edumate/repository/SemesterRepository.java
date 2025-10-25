package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.entity.Semester;
import com.github.hciteam.edumate.model.Term;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
	Optional<Semester> findByTermAndYear(Term term, Long year);
}
