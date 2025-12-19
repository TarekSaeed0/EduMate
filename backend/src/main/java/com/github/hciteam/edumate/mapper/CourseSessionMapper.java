package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.TimeSlot;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.CourseSession;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.TimeSlotRepository;
import com.github.hciteam.edumate.dto.TimeSlotDTO;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.dto.CourseSessionDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.TimeSlotNotFoundException;

@Mapper(componentModel = "spring",
		uses = {CourseOfferingMapper.class, TimeSlotMapper.class})
public abstract class CourseSessionMapper {
	@Autowired
	protected CourseOfferingRepository offeringRepository;
	@Autowired
	protected TimeSlotRepository slotRepository;

	public abstract CourseSessionDTO toDTO(CourseSession session);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "slot", target = "slot", qualifiedByName = "mapSlot")
	public abstract CourseSession toEntity(CourseSessionDTO sessionDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "slot", target = "slot", qualifiedByName = "mapSlot")
	public abstract void updateEntityFromDTO(CourseSessionDTO sessionDTO,
			@MappingTarget CourseSession session);

	@Named("mapOffering")
	protected CourseOffering mapOffering(CourseOfferingDTO offeringDTO) {
		return offeringRepository.findById(offeringDTO.getId()).orElseThrow(
				() -> new CourseOfferingNotFoundException(offeringDTO.getId()));
	}

	@Named("mapSlot")
	protected TimeSlot mapSlot(TimeSlotDTO slotDTO) {
		return slotRepository.findById(slotDTO.getId())
				.orElseThrow(() -> new TimeSlotNotFoundException(slotDTO.getId()));
	}
}
