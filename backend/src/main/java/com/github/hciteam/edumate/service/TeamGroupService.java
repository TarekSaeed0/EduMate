package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.TeamGroup;
import com.github.hciteam.edumate.exception.TeamGroupNotFoundException;
import com.github.hciteam.edumate.mapper.TeamGroupMapper;
import com.github.hciteam.edumate.dto.TeamGroupDTO;
import com.github.hciteam.edumate.repository.TeamGroupRepository;

@Service
public class TeamGroupService {
	private final TeamGroupRepository groupRepository;
	private final TeamGroupMapper groupMapper;

	public TeamGroupService(TeamGroupRepository groupRepository,
			TeamGroupMapper groupMapper) {
		this.groupRepository = groupRepository;
		this.groupMapper = groupMapper;
	}

	public List<TeamGroupDTO> getGroups() {
		return groupRepository.findAll().stream()
				.map(group -> groupMapper.toDTO(group)).toList();
	}

	public TeamGroupDTO createGroup(TeamGroupDTO groupDTO) {
		TeamGroup group = groupMapper.toEntity(groupDTO);

		return groupMapper.toDTO(groupRepository.save(group));
	}

	public TeamGroupDTO getGroup(Long groupId) {
		return groupRepository.findById(groupId)
				.map(group -> groupMapper.toDTO(group))
				.orElseThrow(() -> new TeamGroupNotFoundException());
	}

	public TeamGroupDTO updateGroup(Long groupId, TeamGroupDTO groupDTO) {
		TeamGroup group = groupRepository.findById(groupId).map(existingGroup -> {
			groupMapper.updateEntityFromDTO(groupDTO, existingGroup);
			return existingGroup;
		}).orElseThrow(() -> new TeamGroupNotFoundException());

		return groupMapper.toDTO(groupRepository.save(group));
	}

	public void deleteGroup(Long groupId) {
		if (!groupRepository.existsById(groupId)) {
			throw new TeamGroupNotFoundException();
		}

		groupRepository.deleteById(groupId);
	}
}
