package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.CourseSession;
import com.github.hciteam.edumate.exception.CourseSessionNotFoundException;
import com.github.hciteam.edumate.mapper.CourseSessionMapper;
import com.github.hciteam.edumate.dto.CourseSessionDTO;
import com.github.hciteam.edumate.repository.CourseSessionRepository;

@Service
public class CourseSessionService {
	private final CourseSessionRepository sessionRepository;
	private final CourseSessionMapper sessionMapper;

	public CourseSessionService(CourseSessionRepository sessionRepository,
			CourseSessionMapper sessionMapper) {
		this.sessionRepository = sessionRepository;
		this.sessionMapper = sessionMapper;
	}

	public List<CourseSessionDTO> getSessions() {
		return sessionRepository.findAll().stream().map(sessionMapper::toDTO)
				.toList();
	}

	public CourseSessionDTO createSession(CourseSessionDTO sessionDTO) {
		CourseSession session = sessionMapper.toEntity(sessionDTO);

		return sessionMapper.toDTO(sessionRepository.save(session));
	}

	public CourseSessionDTO getSession(Long sessionId) {
		return sessionRepository.findById(sessionId)
				.map(session -> sessionMapper.toDTO(session))
				.orElseThrow(() -> new CourseSessionNotFoundException(sessionId));
	}

	public CourseSessionDTO updateSession(Long sessionId,
			CourseSessionDTO sessionDTO) {
		CourseSession session =
				sessionRepository.findById(sessionId).map(existingSession -> {
					sessionMapper.updateEntityFromDTO(sessionDTO, existingSession);
					return existingSession;
				}).orElseThrow(() -> new CourseSessionNotFoundException(sessionId));

		return sessionMapper.toDTO(sessionRepository.save(session));
	}

	public void deleteSession(Long sessionId) {
		if (!sessionRepository.existsById(sessionId)) {
			throw new CourseSessionNotFoundException(sessionId);
		}

		sessionRepository.deleteById(sessionId);
	}
}
