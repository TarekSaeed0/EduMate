package com.github.hciteam.edumate.repository;

import java.util.Optional;
import com.github.hciteam.edumate.model.AnnouncementScope;

public interface AnnouncementScopeRepository {
	String getType();

	Optional<AnnouncementScope> findScopeById(Long id);

	boolean existsScopeById(Long id);
}
