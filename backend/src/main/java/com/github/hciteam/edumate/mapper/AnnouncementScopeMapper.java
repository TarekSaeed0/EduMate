package com.github.hciteam.edumate.mapper;

import com.github.hciteam.edumate.dto.AnnouncementScopeDTO;
import com.github.hciteam.edumate.model.AnnouncementScope;

public interface AnnouncementScopeMapper {
	String getScopeType();

	AnnouncementScopeDTO toDTO(AnnouncementScope scope);
}
