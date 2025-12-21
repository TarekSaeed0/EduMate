package com.github.hciteam.edumate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.exception.UnknownAnnouncementScopeTypeException;
import com.github.hciteam.edumate.mapper.AnnouncementScopeMapper;
import com.github.hciteam.edumate.repository.AnnouncementScopeRepository;

@Service
public class AnnouncementScopeService {
	private Map<String, AnnouncementScopeRepository> scopeRepositories =
			new HashMap<>();
	private Map<String, AnnouncementScopeMapper> scopeMappers = new HashMap<>();

	public AnnouncementScopeService(
			List<AnnouncementScopeRepository> scopeRepositories,
			List<AnnouncementScopeMapper> scopeMappers) {
		for (AnnouncementScopeRepository scopeRepository : scopeRepositories) {
			this.scopeRepositories.put(scopeRepository.getScopeType(),
					scopeRepository);
		}

		for (AnnouncementScopeMapper scopeMapper : scopeMappers) {
			this.scopeMappers.put(scopeMapper.getScopeType(), scopeMapper);
		}
	}

	public AnnouncementScopeRepository getScopeRepository(String scopeType) {
		AnnouncementScopeRepository scopeRepository =
				scopeRepositories.get(scopeType);
		if (scopeRepository == null) {
			throw new UnknownAnnouncementScopeTypeException(scopeType);
		}

		return scopeRepository;
	}

	public AnnouncementScopeMapper getScopeMapper(String scopeType) {
		AnnouncementScopeMapper scopeMapper = scopeMappers.get(scopeType);
		if (scopeMapper == null) {
			throw new UnknownAnnouncementScopeTypeException(scopeType);
		}

		return scopeMapper;
	}
}
