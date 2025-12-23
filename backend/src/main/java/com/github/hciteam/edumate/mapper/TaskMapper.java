package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.CourseOffering;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.repository.CourseOfferingRepository;
import com.github.hciteam.edumate.dto.CourseOfferingDTO;
import com.github.hciteam.edumate.dto.TaskDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public abstract class TaskMapper {
	@Autowired
	protected CourseOfferingRepository offeringRepository;

	public abstract TaskDTO toDTO(Task task);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	public abstract Task toEntity(TaskDTO taskDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	public abstract void updateEntityFromDTO(TaskDTO taskDTO,
			@MappingTarget Task task);

	@Named("mapOffering")
	protected CourseOffering mapOffering(CourseOfferingDTO offeringDTO) {
		return offeringRepository.findById(offeringDTO.getId()).orElseThrow(
				() -> new CourseOfferingNotFoundException(offeringDTO.getId()));
	}
}
