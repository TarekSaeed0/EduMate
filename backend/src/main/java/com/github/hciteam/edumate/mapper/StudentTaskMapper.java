package com.github.hciteam.edumate.mapper;

import java.time.LocalDateTime;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.StudentTask;
import com.github.hciteam.edumate.model.StudentTaskStatus;
import com.github.hciteam.edumate.dto.StudentTaskDTO;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface StudentTaskMapper {
	@Mapping(source = "student.id", target = "studentId")
	@Mapping(target = "status", ignore = true)
	StudentTaskDTO toDTO(StudentTask studentTask);

	@AfterMapping
	default void setStatus(StudentTask studentTask,
			@MappingTarget StudentTaskDTO studentTaskDTO) {
		if (studentTask.getSubmittedAt() != null) {
			studentTaskDTO.setStatus(StudentTaskStatus.COMPLETED);
		} else if (studentTask.getTask().getDueDate() != null
				&& studentTask.getTask().getDueDate().isBefore(LocalDateTime.now())) {
			studentTaskDTO.setStatus(StudentTaskStatus.OVERDUE);
		} else {
			studentTaskDTO.setStatus(StudentTaskStatus.UPCOMING);
		}
	}
}
