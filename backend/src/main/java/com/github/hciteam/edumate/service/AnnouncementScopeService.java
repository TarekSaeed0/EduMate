package com.github.hciteam.edumate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.exception.AnnouncementScopeNotFoundException;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.repository.AnnouncementScopeRepository;

@Service
public class AnnouncementScopeService {
	Map<String, AnnouncementScopeRepository> scopeRepositories = new HashMap<>();

	public AnnouncementScopeService(
			List<AnnouncementScopeRepository> scopeRepositories) {
		for (AnnouncementScopeRepository scopeRepository : scopeRepositories) {
			this.scopeRepositories.put(scopeRepository.getType(), scopeRepository);
		}
	}

	private AnnouncementScopeRepository getRepository(String scopeType) {
		AnnouncementScopeRepository scopeRepository =
				scopeRepositories.get(scopeType);
		if (scopeRepository == null) {
			throw new IllegalArgumentException(
					"Unsupported AnnouncementScope type: " + scopeType);
		}
		return scopeRepository;
	}

	public AnnouncementScope getScope(String scopeType, Long scopeId) {
		AnnouncementScopeRepository scopeRepository = getRepository(scopeType);

		return scopeRepository.findScopeById(scopeId)
				.map(scope -> (AnnouncementScope) scope).orElseThrow(
						() -> new AnnouncementScopeNotFoundException(scopeType, scopeId));
	}
}
