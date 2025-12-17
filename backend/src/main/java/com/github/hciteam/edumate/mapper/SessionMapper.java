package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.TimeSlot;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.Session;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.repository.TimeSlotRepository;
import com.github.hciteam.edumate.dto.TimeSlotDTO;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.dto.SessionDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;
import com.github.hciteam.edumate.exception.TimeSlotNotFoundException;

@Mapper(componentModel = "spring",
		uses = {CourseOfferingMapper.class, TimeSlotMapper.class})
public abstract class SessionMapper {
	@Autowired
	protected CourseOfferingRepository offeringRepository;
	@Autowired
	protected TimeSlotRepository slotRepository;

	public abstract SessionDTO toDTO(Session session);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "slot", target = "slot", qualifiedByName = "mapSlot")
	public abstract Session toEntity(SessionDTO sessionDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(source = "slot", target = "slot", qualifiedByName = "mapSlot")
	public abstract void updateEntityFromDTO(SessionDTO sessionDTO,
			@MappingTarget Session session);

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
