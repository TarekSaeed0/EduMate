package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.Task;
import com.github.hciteam.edumate.model.TaskDTO;

@Mapper(componentModel = "spring")
public interface TaskMapper {
	@Mapping(source = "offering.id", target = "offeringId")
	TaskDTO toDTO(Task task);

	@Mapping(source = "offeringId", target = "offering.id")
	Task toEntity(TaskDTO taskDTO);
}
