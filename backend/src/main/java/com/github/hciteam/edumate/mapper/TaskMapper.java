package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.dto.TaskDTO;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public interface TaskMapper {
	TaskDTO toDTO(Task task);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	Task toEntity(TaskDTO taskDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "offering", target = "offering",
			qualifiedByName = "mapOffering")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "studentTasks", ignore = true)
	void updateEntityFromDTO(TaskDTO taskDTO, @MappingTarget Task task);
}
