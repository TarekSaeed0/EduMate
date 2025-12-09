package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.mapper.TeamJoinInviteMapper;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;
import com.github.hciteam.edumate.exception.StudentNotFoundException;
import com.github.hciteam.edumate.exception.TeamJoinInviteNotFoundException;
import com.github.hciteam.edumate.exception.TeamNotFoundException;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.TeamJoinInviteRepository;
import com.github.hciteam.edumate.repository.TeamRepository;
import com.github.hciteam.edumate.specification.TeamJoinInviteSpecifications;

@Service
public class TeamJoinInviteService {
	private final TeamJoinInviteRepository inviteRepository;
	private final TeamRepository teamRepository;
	private final StudentRepository studentRepository;
	private final TeamJoinInviteMapper inviteMapper;

	public TeamJoinInviteService(TeamJoinInviteRepository inviteRepository,
			TeamRepository teamRepository, StudentRepository studentRepository,
			TeamJoinInviteMapper inviteMapper) {
		this.inviteRepository = inviteRepository;
		this.teamRepository = teamRepository;
		this.studentRepository = studentRepository;
		this.inviteMapper = inviteMapper;
	}

	public List<TeamJoinInviteDTO> getInvites(Long teamId, Long studentId,
			TeamJoinStatus status) {

		Specification<TeamJoinInvite> specification = Specification.unrestricted();

		if (teamId != null) {
			specification =
					specification.and(TeamJoinInviteSpecifications.ofTeam(teamId));
		}

		if (studentId != null) {
			specification =
					specification.and(TeamJoinInviteSpecifications.ofStudent(studentId));
		}

		if (status != null) {
			specification =
					specification.and(TeamJoinInviteSpecifications.ofStatus(status));
		}

		return inviteRepository.findAll(specification).stream()
				.map(inviteMapper::toDTO).toList();
	}

	public TeamJoinInviteDTO createInvite(TeamJoinInviteDTO inviteDTO) {
		Team team = teamRepository.findById(inviteDTO.getTeam().getId())
				.orElseThrow(() -> new TeamNotFoundException());
		Student student = studentRepository.findById(inviteDTO.getStudent().getId())
				.orElseThrow(() -> new StudentNotFoundException());

		TeamJoinInvite invite = TeamJoinInvite.builder().team(team).student(student)
				.status(TeamJoinStatus.PENDING).build();

		return inviteMapper.toDTO(inviteRepository.save(invite));

	}

	public TeamJoinInviteDTO getInvite(Long inviteId) {
		return inviteRepository.findById(inviteId).map(inviteMapper::toDTO)
				.orElseThrow(() -> new TeamJoinInviteNotFoundException());
	}

	public void acceptInvite(Long inviteId) {
		TeamJoinInvite invite =
				inviteRepository.findById(inviteId).map(existingInvite -> {
					existingInvite.setStatus(TeamJoinStatus.ACCEPTED);
					return existingInvite;
				}).orElseThrow(() -> new TeamJoinInviteNotFoundException());

		inviteRepository.save(invite);
	}

	public void rejectInvite(Long inviteId) {
		TeamJoinInvite invite =
				inviteRepository.findById(inviteId).map(existingInvite -> {
					existingInvite.setStatus(TeamJoinStatus.REJECTED);
					return existingInvite;
				}).orElseThrow(() -> new TeamJoinInviteNotFoundException());

		inviteRepository.save(invite);
	}

	public void deleteInvite(Long inviteId) {
		if (!inviteRepository.existsById(inviteId)) {
			throw new TeamJoinInviteNotFoundException();
		}

		inviteRepository.deleteById(inviteId);
	}
}
