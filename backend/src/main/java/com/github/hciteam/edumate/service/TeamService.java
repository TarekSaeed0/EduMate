package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamGroup;
import com.github.hciteam.edumate.exception.StudentNotFoundException;
import com.github.hciteam.edumate.exception.TeamGroupNotFoundException;
import com.github.hciteam.edumate.exception.TeamNotFoundException;
import com.github.hciteam.edumate.mapper.TeamMapper;
import com.github.hciteam.edumate.dto.TeamDTO;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.TeamGroupRepository;
import com.github.hciteam.edumate.repository.TeamRepository;

@Service
public class TeamService {
	private final TeamRepository teamRepository;
	private final TeamGroupRepository groupRepository;
	private final StudentRepository studentRepository;
	private final TeamMapper teamMapper;

	public TeamService(TeamRepository teamRepository,
			StudentRepository studentRepository, TeamGroupRepository groupRepository,
			TeamMapper teamMapper) {
		this.teamRepository = teamRepository;
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
		this.teamMapper = teamMapper;
	}

	public List<TeamDTO> getTeams() {
		return teamRepository.findAll().stream().map(team -> teamMapper.toDTO(team))
				.toList();
	}

	public TeamDTO createTeam(TeamDTO teamDTO) {
		TeamGroup group = groupRepository.findById(teamDTO.getGroup().getId())
				.orElseThrow(() -> new TeamGroupNotFoundException());
		Student leader = studentRepository.findById(teamDTO.getLeader().getId())
				.orElseThrow(() -> new StudentNotFoundException());

		Team team = Team.builder().group(group).leader(leader).build();

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
