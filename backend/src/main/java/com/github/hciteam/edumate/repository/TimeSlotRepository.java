package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
