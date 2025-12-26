package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	Optional<Permission> findByName(String name);

	boolean existsByName(String name);
}
