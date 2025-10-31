package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.hciteam.edumate.entity.Task;
import com.github.hciteam.edumate.model.TaskDTO;

@Mapper(componentModel = "spring", uses = {SemesterCourseMapper.class})
public interface TaskMapper {
	TaskDTO toDTO(Task task);

	Task toEntity(TaskDTO taskDTO);
}
