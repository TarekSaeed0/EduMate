package com.github.hciteam.edumate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.TimeSlot;
import com.github.hciteam.edumate.model.WeekDay;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
	Optional<TimeSlot> findByPeriodIdAndWeekDay(Long periodId, WeekDay weekDay);

	boolean existsByPeriodIdAndWeekDay(Long periodId, WeekDay weekDay);
}
