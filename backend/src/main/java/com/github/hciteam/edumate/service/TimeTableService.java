package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.SessionDTO;
import com.github.hciteam.edumate.dto.TimeTableDTO;
import com.github.hciteam.edumate.mapper.SessionMapper;
import com.github.hciteam.edumate.repository.SessionRepository;

@Service
public class TimeTableService {
	private final SessionRepository sessionRepository;
	private final SessionMapper sessionMapper;

	public TimeTableService(SessionRepository sessionRepository,
			SessionMapper sessionMapper) {
		this.sessionRepository = sessionRepository;
		this.sessionMapper = sessionMapper;
	}

	public TimeTableDTO fromOfferings(List<Long> offeringIds) {
		List<SessionDTO> sessions =
				sessionRepository.findByOfferingIdIn(offeringIds).stream()
						.map(sessionMapper::toDTO).toList();

		return TimeTableDTO.fromSessions(sessions);
	}
}
