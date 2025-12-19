package com.github.hciteam.edumate.repository;

import java.util.Optional;
import com.github.hciteam.edumate.model.AnnouncementScope;

public interface AnnouncementScopeRepository {
	String getScopeType();

	Optional<AnnouncementScope> findScopeById(Long id);

	boolean existsScopeById(Long id);
}
