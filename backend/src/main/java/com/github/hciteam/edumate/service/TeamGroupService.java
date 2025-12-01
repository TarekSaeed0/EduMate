package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.TeamGroup;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.TeamGroupNotFoundException;
import com.github.hciteam.edumate.mapper.TeamGroupMapper;
import com.github.hciteam.edumate.dto.TeamGroupDTO;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.TeamGroupRepository;

@Service
public class TeamGroupService {
	private final TeamGroupRepository groupRepository;
	private final CourseOfferingRepository offeringRepository;
	private final TeamGroupMapper groupMapper;

	public TeamGroupService(TeamGroupRepository groupRepository,
			CourseOfferingRepository offeringRepository,
			TeamGroupMapper groupMapper) {
		this.groupRepository = groupRepository;
		this.offeringRepository = offeringRepository;
		this.groupMapper = groupMapper;
	}

	public List<TeamGroupDTO> getGroups() {
		return groupRepository.findAll().stream()
				.map(group -> groupMapper.toDTO(group)).toList();
	}

	public TeamGroupDTO createGroup(TeamGroupDTO groupDTO) {
		CourseOffering offering =
				offeringRepository.findById(groupDTO.getOffering().getId())
						.orElseThrow(() -> new CourseOfferingNotFoundException());

		TeamGroup group =
				TeamGroup.builder().offering(offering).name(groupDTO.getName())
						.minimumMemberCount(groupDTO.getMinimumMemberCount())
						.maximumMemberCount(groupDTO.getMaximumMemberCount()).build();

		return groupMapper.toDTO(groupRepository.save(group));
	}

	public TeamGroupDTO getGroup(Long groupId) {
		return groupRepository.findById(groupId)
				.map(group -> groupMapper.toDTO(group))
				.orElseThrow(() -> new TeamGroupNotFoundException());
	}

	public void deleteGroup(Long groupId) {
		if (!groupRepository.existsById(groupId)) {
			throw new TeamGroupNotFoundException();
		}

		groupRepository.deleteById(groupId);
	}
}
