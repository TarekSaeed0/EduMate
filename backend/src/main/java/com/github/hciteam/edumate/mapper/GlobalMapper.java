package com.github.hciteam.edumate.mapper;

import org.springframework.stereotype.Component;
import com.github.hciteam.edumate.dto.AnnouncementScopeDTO;
import com.github.hciteam.edumate.dto.GlobalDTO;
import com.github.hciteam.edumate.model.AnnouncementScope;

@Component
public class GlobalMapper implements AnnouncementScopeMapper {
	@Override
	public String getScopeType() {
		return "GLOBAL";
	}

	@Override
	public AnnouncementScopeDTO toDTO(AnnouncementScope scope) {
		if (scope == null) {
			return null;
		}

		return new GlobalDTO();
	}
}
