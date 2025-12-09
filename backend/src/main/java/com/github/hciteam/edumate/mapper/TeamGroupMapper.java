package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.TeamGroup;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.dto.TeamGroupDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public abstract class TeamGroupMapper {
	@Autowired
	protected CourseOfferingRepository offeringRepository;

	public abstract TeamGroupDTO toDTO(TeamGroup group);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(target = "teams", ignore = true)
	public abstract TeamGroup toEntity(TeamGroupDTO groupDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(target = "teams", ignore = true)
	public abstract void updateEntityFromDTO(TeamGroupDTO groupDTO,
			@MappingTarget TeamGroup group);

	@Named("mapOffering")
	protected CourseOffering mapOffering(CourseOfferingDTO offeringDTO) {
		return offeringRepository.findById(offeringDTO.getId())
				.orElseThrow(() -> new CourseOfferingNotFoundException());
	}
}
