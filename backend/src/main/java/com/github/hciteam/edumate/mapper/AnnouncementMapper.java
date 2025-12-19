package com.github.hciteam.edumate.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Announcement;
import com.github.hciteam.edumate.service.AnnouncementScopeService;
import com.github.hciteam.edumate.dto.AnnouncementDTO;

@Mapper(componentModel = "spring")
public abstract class AnnouncementMapper {
	@Autowired
	protected AnnouncementScopeService scopeService;

	public abstract AnnouncementDTO toDTO(Announcement announcement);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "scope", ignore = true)
	public abstract Announcement toEntity(AnnouncementDTO announcementDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "scope", ignore = true)
	public abstract void updateEntityFromDTO(AnnouncementDTO announcementDTO,
			@MappingTarget Announcement announcement);


	@AfterMapping
	protected void setScope(AnnouncementDTO announcementDTO,
			@MappingTarget Announcement announcement) {
		announcement.setScope(scopeService.getScope(announcementDTO.getScopeType(),
				announcementDTO.getScopeId()));
	}
}
