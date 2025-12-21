package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.model.Global;

@Component
public class GlobalRepository implements AnnouncementScopeRepository {
	private final Global global;

	public GlobalRepository(Global global) {
		this.global = global;
	}

	@Override
	public String getScopeType() {
		return "GLOBAL";
	}

	@Override
	public Optional<AnnouncementScope> findScopeById(Long id) {
		if (id.equals(0L)) {
			return Optional.of(global);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean existsScopeById(Long id) {
		return id.equals(0L);
	}
}

