package com.github.hciteam.edumate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "offering_id", nullable = false)
	private CourseOffering offering;

	@ManyToOne
	@JoinColumn(name = "slot_id", nullable = false)
	private TimeSlot slot;

	private String location;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseSessionType type;

	public CourseSession(CourseOffering offering, TimeSlot slot, String location,
			CourseSessionType type) {
		this.offering = offering;
		this.slot = slot;
		this.location = location;
		this.type = type;
	}
}
