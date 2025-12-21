package com.github.hciteam.edumate.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Announcement;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.repository.AnnouncementScopeRepository;
import com.github.hciteam.edumate.service.AnnouncementScopeService;
import com.github.hciteam.edumate.dto.AnnouncementDTO;
import com.github.hciteam.edumate.exception.AnnouncementScopeNotFoundException;

@Mapper(componentModel = "spring")
public abstract class AnnouncementMapper {
	@Autowired
	protected AnnouncementScopeService scopeService;

	@Mapping(target = "scopeType", source = "scopeType")
	@Mapping(target = "scopeId", source = "scopeId")
	@Mapping(target = "scope", ignore = true)
	public abstract AnnouncementDTO toDTO(Announcement announcement);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "scope", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	public abstract Announcement toEntity(AnnouncementDTO announcementDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "scope", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	public abstract void updateEntityFromDTO(AnnouncementDTO announcementDTO,
			@MappingTarget Announcement announcement);

	@AfterMapping
	protected void setScopeDTO(Announcement announcement,
			@MappingTarget AnnouncementDTO announcementDTO) {
		AnnouncementScopeMapper scopeMapper =
				scopeService.getScopeMapper(announcement.getScopeType());

		announcementDTO.setScope(scopeMapper.toDTO(announcement.getScope()));
	}

	@AfterMapping
	protected void setScope(AnnouncementDTO announcementDTO,
			@MappingTarget Announcement announcement) {
		AnnouncementScopeRepository scopeRepository =
				scopeService.getScopeRepository(announcementDTO.getScopeType());

		announcement
				.setScope(
						scopeRepository.findScopeById(announcementDTO.getScopeId())
								.map(scope -> (AnnouncementScope) scope)
								.orElseThrow(() -> new AnnouncementScopeNotFoundException(
										announcementDTO.getScopeType(),
										announcementDTO.getScopeId())));
	}
}
