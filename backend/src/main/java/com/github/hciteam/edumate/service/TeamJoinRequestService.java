package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamJoinRequest;
import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.mapper.TeamJoinRequestMapper;
import com.github.hciteam.edumate.dto.TeamJoinRequestDTO;
import com.github.hciteam.edumate.exception.StudentNotFoundException;
import com.github.hciteam.edumate.exception.TeamJoinRequestNotFoundException;
import com.github.hciteam.edumate.exception.TeamNotFoundException;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.TeamJoinRequestRepository;
import com.github.hciteam.edumate.repository.TeamRepository;
import com.github.hciteam.edumate.specification.TeamJoinRequestSpecifications;

@Service
public class TeamJoinRequestService {
	private final TeamJoinRequestRepository requestRepository;
	private final TeamRepository teamRepository;
	private final StudentRepository studentRepository;
	private final TeamJoinRequestMapper requestMapper;

	public TeamJoinRequestService(TeamJoinRequestRepository requestRepository,
			TeamRepository teamRepository, StudentRepository studentRepository,
			TeamJoinRequestMapper requestMapper) {
		this.requestRepository = requestRepository;
		this.teamRepository = teamRepository;
		this.studentRepository = studentRepository;
		this.requestMapper = requestMapper;
	}

	public List<TeamJoinRequestDTO> getRequests(Long teamId, Long studentId,
			TeamJoinStatus status) {

		Specification<TeamJoinRequest> specification = Specification.unrestricted();

		if (teamId != null) {
			specification =
					specification.and(TeamJoinRequestSpecifications.ofTeam(teamId));
		}

		if (studentId != null) {
			specification =
					specification.and(TeamJoinRequestSpecifications.ofStudent(studentId));
		}

		if (status != null) {
			specification =
					specification.and(TeamJoinRequestSpecifications.ofStatus(status));
		}

		return requestRepository.findAll(specification).stream()
				.map(requestMapper::toDTO).toList();
	}

	public TeamJoinRequestDTO createRequest(TeamJoinRequestDTO requestDTO) {
		Team team = teamRepository.findById(requestDTO.getTeam().getId())
				.orElseThrow(() -> new TeamNotFoundException());
		Student student =
				studentRepository.findById(requestDTO.getStudent().getId())
						.orElseThrow(() -> new StudentNotFoundException());

		TeamJoinRequest request = TeamJoinRequest.builder().team(team)
				.student(student).status(TeamJoinStatus.PENDING).build();

		return requestMapper.toDTO(requestRepository.save(request));

	}

	public TeamJoinRequestDTO getRequest(Long requestId) {
		return requestRepository.findById(requestId).map(requestMapper::toDTO)
				.orElseThrow(() -> new TeamJoinRequestNotFoundException());
	}

	public void acceptRequest(Long requestId) {
		TeamJoinRequest request =
				requestRepository.findById(requestId).map(existingRequest -> {
					existingRequest.setStatus(TeamJoinStatus.ACCEPTED);
					return existingRequest;
				}).orElseThrow(() -> new TeamJoinRequestNotFoundException());

		requestRepository.save(request);
	}

	public void rejectRequest(Long requestId) {
		TeamJoinRequest request =
				requestRepository.findById(requestId).map(existingRequest -> {
					existingRequest.setStatus(TeamJoinStatus.REJECTED);
					return existingRequest;
				}).orElseThrow(() -> new TeamJoinRequestNotFoundException());

		requestRepository.save(request);
	}

	public void deleteRequest(Long requestId) {
		if (!requestRepository.existsById(requestId)) {
			throw new TeamJoinRequestNotFoundException();
		}

		requestRepository.deleteById(requestId);
	}
}
