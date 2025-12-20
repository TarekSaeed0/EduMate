package com.github.hciteam.edumate.repository;

import java.time.LocalTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.TimePeriod;

public interface TimePeriodRepository extends JpaRepository<TimePeriod, Long> {
	Optional<TimePeriod> findByStartTimeAndEndTime(LocalTime startTime,
			LocalTime endTime);

	boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
}
