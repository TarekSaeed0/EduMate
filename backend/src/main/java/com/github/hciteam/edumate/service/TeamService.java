package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamStatus;
import com.github.hciteam.edumate.exception.TeamNotFoundException;
import com.github.hciteam.edumate.mapper.TeamMapper;
import com.github.hciteam.edumate.dto.TeamDTO;
import com.github.hciteam.edumate.repository.TeamRepository;
import com.github.hciteam.edumate.specification.TeamSpecifications;

@Service
public class TeamService {
	private final TeamRepository teamRepository;
	private final TeamMapper teamMapper;

	public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
		this.teamRepository = teamRepository;
		this.teamMapper = teamMapper;
	}

	public List<TeamDTO> getTeams(Long groupId, Long leaderId, Long memberId,
			TeamStatus status) {

		Specification<Team> specification = Specification.unrestricted();

		if (groupId != null) {
			specification = specification.and(TeamSpecifications.ofGroup(groupId));
		}

		if (leaderId != null) {
			specification = specification.and(TeamSpecifications.ofLeader(leaderId));
		}

		if (memberId != null) {
			specification = specification.and(TeamSpecifications.isMember(memberId));
		}

		if (status != null) {
			specification = specification.and(switch (status) {
				case INCOMPLETE -> TeamSpecifications.isIncomplete();
				case SUFFICIENT -> TeamSpecifications.isSufficient();
				case COMPLETE -> TeamSpecifications.isComplete();
			});
		}

		return teamRepository.findAll(specification).stream().map(teamMapper::toDTO)
				.toList();
	}

	public TeamDTO createTeam(TeamDTO teamDTO) {
		Team team = teamMapper.toEntity(teamDTO);

		return teamMapper.toDTO(teamRepository.save(team));
	}

	public TeamDTO getTeam(Long teamId) {
		return teamRepository.findById(teamId).map(team -> teamMapper.toDTO(team))
				.orElseThrow(() -> new TeamNotFoundException());
	}

	public void deleteTeam(Long teamId) {
		if (!teamRepository.existsById(teamId)) {
			throw new TeamNotFoundException();
		}

		teamRepository.deleteById(teamId);
	}
}
