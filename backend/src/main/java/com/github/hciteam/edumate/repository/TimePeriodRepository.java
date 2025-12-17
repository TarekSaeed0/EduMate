package com.github.hciteam.edumate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.hciteam.edumate.model.TimePeriod;

public interface TimePeriodRepository extends JpaRepository<TimePeriod, Long> {
}
