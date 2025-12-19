package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.TimePeriod;
import com.github.hciteam.edumate.dto.TimePeriodDTO;

@Mapper(componentModel = "spring")
public interface TimePeriodMapper {
	TimePeriodDTO toDTO(TimePeriod period);

	@Mapping(target = "id", ignore = true)
	TimePeriod toEntity(TimePeriodDTO periodDTO);

	@Mapping(target = "id", ignore = true)
	void updateEntityFromDTO(TimePeriodDTO periodDTO,
			@MappingTarget TimePeriod period);
}
