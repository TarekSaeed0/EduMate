package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.TimePeriodDTO;
import com.github.hciteam.edumate.exception.TimePeriodNotFoundException;
import com.github.hciteam.edumate.mapper.TimePeriodMapper;
import com.github.hciteam.edumate.model.TimePeriod;
import com.github.hciteam.edumate.repository.TimePeriodRepository;

@Service
public class TimePeriodService {
	private final TimePeriodRepository periodRepository;
	private final TimePeriodMapper periodMapper;

	public TimePeriodService(TimePeriodRepository periodRepository,
			TimePeriodMapper periodMapper) {
		this.periodRepository = periodRepository;
		this.periodMapper = periodMapper;
	}

	public List<TimePeriodDTO> getPeriods() {
		return periodRepository.findAll().stream().map(periodMapper::toDTO)
				.toList();
	}

	public TimePeriodDTO createPeriod(TimePeriodDTO periodDTO) {
		TimePeriod period = periodMapper.toEntity(periodDTO);

		return periodMapper.toDTO(periodRepository.save(period));
	}

	public TimePeriodDTO getPeriod(Long periodId) {
		return periodRepository.findById(periodId)
				.map(period -> periodMapper.toDTO(period))
				.orElseThrow(() -> new TimePeriodNotFoundException(periodId));
	}

	public TimePeriodDTO updatePeriod(Long periodId, TimePeriodDTO periodDTO) {
		TimePeriod period =
				periodRepository.findById(periodId).map(existingPeriod -> {
					periodMapper.updateEntityFromDTO(periodDTO, existingPeriod);
					return existingPeriod;
				}).orElseThrow(() -> new TimePeriodNotFoundException(periodId));

		return periodMapper.toDTO(periodRepository.save(period));
	}

	public void deletePeriod(Long periodId) {
		if (!periodRepository.existsById(periodId)) {
			throw new TimePeriodNotFoundException(periodId);
		}

		periodRepository.deleteById(periodId);
	}
}
