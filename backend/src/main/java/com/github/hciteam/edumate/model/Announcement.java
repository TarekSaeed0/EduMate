package com.github.hciteam.edumate.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter(AccessLevel.NONE)
	@Column(nullable = false)
	private String scopeType;

	@Setter(AccessLevel.NONE)
	@Column(nullable = false)
	private Long scopeId;

	@Transient
	private AnnouncementScope scope;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	public void setScope(AnnouncementScope scope) {
		this.scope = scope;
		if (scope != null) {
			this.scopeType = scope.getScopeType();
			this.scopeId = scope.getScopeId();
		} else {
			this.scopeType = null;
			this.scopeId = null;
		}
	}
}
