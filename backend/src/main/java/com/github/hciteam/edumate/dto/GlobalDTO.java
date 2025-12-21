package com.github.hciteam.edumate.dto;

public class GlobalDTO implements AnnouncementScopeDTO {
	@Override
	public String getScopeType() {
		return "GLOBAL";
	}

	@Override
	public Long getScopeId() {
		return 0L;
	}
}
