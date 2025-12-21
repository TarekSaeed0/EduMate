package com.github.hciteam.edumate.model;

import org.springframework.stereotype.Component;

@Component
public class Global implements AnnouncementScope {
	@Override
	public String getScopeType() {
		return "GLOBAL";
	}

	@Override
	public Long getScopeId() {
		return 0L;
	}
}
