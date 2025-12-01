package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.TeamJoinInvite;
import com.github.hciteam.edumate.model.TeamJoinStatus;
import com.github.hciteam.edumate.mapper.TeamJoinInviteMapper;
import com.github.hciteam.edumate.dto.TeamJoinInviteDTO;
import com.github.hciteam.edumate.exception.TeamJoinInviteNotFoundException;
import com.github.hciteam.edumate.repository.TeamJoinInviteRepository;
import com.github.hciteam.edumate.specification.TeamJoinInviteSpecifications;

@Service
public class TeamJoinInviteService {
	private final TeamJoinInviteRepository inviteRepository;
	private final TeamJoinInviteMapper inviteMapper;

	public TeamJoinInviteService(TeamJoinInviteRepository inviteRepository,
			TeamJoinInviteMapper inviteMapper) {
		this.inviteRepository = inviteRepository;
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

	public TeamJoinInviteDTO getInvite(Long inviteId) {
		return inviteRepository.findById(inviteId).map(inviteMapper::toDTO)
				.orElseThrow(() -> new TeamJoinInviteNotFoundException());
	}
}
