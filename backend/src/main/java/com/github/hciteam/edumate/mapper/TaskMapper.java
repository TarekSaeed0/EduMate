package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.model.Task;
import com.github.hciteam.edumate.dto.TaskDTO;

@Mapper(componentModel = "spring", uses = {CourseOfferingMapper.class})
public interface TaskMapper {
	TaskDTO toDTO(Task task);

	@Mapping(target = "studentTasks", ignore = true)
	Task toEntity(TaskDTO taskDTO);
}
