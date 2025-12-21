package com.github.hciteam.edumate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AnnouncementScopeDTO {
	@JsonIgnore
	String getScopeType();

	@JsonIgnore
	Long getScopeId();
}
