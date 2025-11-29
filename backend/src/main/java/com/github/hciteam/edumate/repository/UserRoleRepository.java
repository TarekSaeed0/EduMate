package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	Optional<UserRole> findByName(String name);

	boolean existsByName(String name);
}
