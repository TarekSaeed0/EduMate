package com.github.hciteam.edumate.model;

import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "time_slots",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"period_id", "weekDay"})})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "period_id", nullable = false)
	private TimePeriod period;

	@Column(nullable = false)
	private WeekDay weekDay;
}
