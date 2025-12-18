package com.github.hciteam.edumate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.exception.AnnouncementScopeNotFoundException;
import com.github.hciteam.edumate.exception.UnknownAnnouncementScopeTypeException;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.repository.AnnouncementScopeRepository;

@Service
public class AnnouncementScopeService {
	private Map<String, AnnouncementScopeRepository> scopeRepositories =
			new HashMap<>();

	public AnnouncementScopeService(
			List<AnnouncementScopeRepository> scopeRepositories) {
		for (AnnouncementScopeRepository scopeRepository : scopeRepositories) {
			this.scopeRepositories.put(scopeRepository.getScopeType(),
					scopeRepository);
		}
	}

	private AnnouncementScopeRepository getScopeRepository(String scopeType) {
		AnnouncementScopeRepository scopeRepository =
				scopeRepositories.get(scopeType);
		if (scopeRepository == null) {
			throw new UnknownAnnouncementScopeTypeException(scopeType);
		}
		return scopeRepository;
	}

	public AnnouncementScope getScope(String scopeType, Long scopeId) {
		AnnouncementScopeRepository scopeRepository = getScopeRepository(scopeType);

		return scopeRepository.findScopeById(scopeId)
				.map(scope -> (AnnouncementScope) scope).orElseThrow(
						() -> new AnnouncementScopeNotFoundException(scopeType, scopeId));
	}
}
