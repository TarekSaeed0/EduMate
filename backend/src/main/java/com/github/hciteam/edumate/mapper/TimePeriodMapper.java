package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.TimePeriod;
import com.github.hciteam.edumate.repository.TimePeriodRepository;
import com.github.hciteam.edumate.dto.TimePeriodDTO;
import com.github.hciteam.edumate.exception.TimePeriodNotFoundException;

@Mapper(componentModel = "spring")
public abstract class TimePeriodMapper {
	@Autowired
	protected TimePeriodRepository periodRepository;

	public abstract TimePeriodDTO toDTO(TimePeriod period);

	@Mapping(target = "id", ignore = true)
	public abstract TimePeriod toEntity(TimePeriodDTO periodDTO);

	@Mapping(target = "id", ignore = true)
	public abstract void updateEntityFromDTO(TimePeriodDTO periodDTO,
			@MappingTarget TimePeriod period);

	@Named("mapPeriod")
	protected TimePeriod mapPeriod(TimePeriodDTO periodDTO) {
		return periodRepository.findById(periodDTO.getId())
				.orElseThrow(() -> new TimePeriodNotFoundException(periodDTO.getId()));
	}
}
