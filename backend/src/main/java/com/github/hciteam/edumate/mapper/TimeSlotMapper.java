package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.TimePeriod;
import com.github.hciteam.edumate.model.TimeSlot;
import com.github.hciteam.edumate.repository.TimePeriodRepository;
import com.github.hciteam.edumate.dto.TimePeriodDTO;
import com.github.hciteam.edumate.dto.TimeSlotDTO;
import com.github.hciteam.edumate.exception.TimePeriodNotFoundException;

@Mapper(componentModel = "spring", uses = {TimePeriodMapper.class})
public abstract class TimeSlotMapper {
	@Autowired
	protected TimePeriodRepository periodRepository;

	public abstract TimeSlotDTO toDTO(TimeSlot slot);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "period", target = "period", qualifiedByName = "mapPeriod")
	public abstract TimeSlot toEntity(TimeSlotDTO slotDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "period", target = "period", qualifiedByName = "mapPeriod")
	public abstract void updateEntityFromDTO(TimeSlotDTO slotDTO,
			@MappingTarget TimeSlot slot);

	@Named("mapPeriod")
	protected TimePeriod mapPeriod(TimePeriodDTO periodDTO) {
		return periodRepository.findById(periodDTO.getId())
				.orElseThrow(() -> new TimePeriodNotFoundException(periodDTO.getId()));
	}
}
