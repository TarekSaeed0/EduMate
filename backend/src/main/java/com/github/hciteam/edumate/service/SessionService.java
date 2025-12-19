package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Session;
import com.github.hciteam.edumate.exception.SessionNotFoundException;
import com.github.hciteam.edumate.mapper.SessionMapper;
import com.github.hciteam.edumate.dto.SessionDTO;
import com.github.hciteam.edumate.repository.SessionRepository;

@Service
public class SessionService {
	private final SessionRepository sessionRepository;
	private final SessionMapper sessionMapper;

	public SessionService(SessionRepository sessionRepository,
			SessionMapper sessionMapper) {
		this.sessionRepository = sessionRepository;
		this.sessionMapper = sessionMapper;
	}

	public List<SessionDTO> getSessions() {
		return sessionRepository.findAll().stream().map(sessionMapper::toDTO)
				.toList();
	}

	public SessionDTO createSession(SessionDTO sessionDTO) {
		Session session = sessionMapper.toEntity(sessionDTO);

		return sessionMapper.toDTO(sessionRepository.save(session));
	}

	public SessionDTO getSession(Long sessionId) {
		return sessionRepository.findById(sessionId)
				.map(session -> sessionMapper.toDTO(session))
				.orElseThrow(() -> new SessionNotFoundException(sessionId));
	}

	public SessionDTO updateSession(Long sessionId, SessionDTO sessionDTO) {
		Session session =
				sessionRepository.findById(sessionId).map(existingSession -> {
					sessionMapper.updateEntityFromDTO(sessionDTO, existingSession);
					return existingSession;
				}).orElseThrow(() -> new SessionNotFoundException(sessionId));

		return sessionMapper.toDTO(sessionRepository.save(session));
	}

	public void deleteSession(Long sessionId) {
		if (!sessionRepository.existsById(sessionId)) {
			throw new SessionNotFoundException(sessionId);
		}

		sessionRepository.deleteById(sessionId);
	}
}
