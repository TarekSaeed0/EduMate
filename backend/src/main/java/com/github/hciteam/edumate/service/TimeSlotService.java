package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.TimeSlotDTO;
import com.github.hciteam.edumate.exception.TimeSlotNotFoundException;
import com.github.hciteam.edumate.mapper.TimeSlotMapper;
import com.github.hciteam.edumate.model.TimeSlot;
import com.github.hciteam.edumate.repository.TimeSlotRepository;

@Service
public class TimeSlotService {
	private final TimeSlotRepository slotRepository;
	private final TimeSlotMapper slotMapper;

	public TimeSlotService(TimeSlotRepository slotRepository,
			TimeSlotMapper slotMapper) {
		this.slotRepository = slotRepository;
		this.slotMapper = slotMapper;
	}

	public List<TimeSlotDTO> getSlots() {
		return slotRepository.findAll().stream().map(slotMapper::toDTO).toList();
	}

	public TimeSlotDTO createSlot(TimeSlotDTO slotDTO) {
		TimeSlot slot = slotMapper.toEntity(slotDTO);

		return slotMapper.toDTO(slotRepository.save(slot));
	}

	public TimeSlotDTO getSlot(Long slotId) {
		return slotRepository.findById(slotId).map(slot -> slotMapper.toDTO(slot))
				.orElseThrow(() -> new TimeSlotNotFoundException(slotId));
	}

	public TimeSlotDTO updateSlot(Long slotId, TimeSlotDTO slotDTO) {
		TimeSlot slot = slotRepository.findById(slotId).map(existingSlot -> {
			slotMapper.updateEntityFromDTO(slotDTO, existingSlot);
			return existingSlot;
		}).orElseThrow(() -> new TimeSlotNotFoundException(slotId));

		return slotMapper.toDTO(slotRepository.save(slot));
	}

	public void deleteSlot(Long slotId) {
		if (!slotRepository.existsById(slotId)) {
			throw new TimeSlotNotFoundException(slotId);
		}

		slotRepository.deleteById(slotId);
	}
}
