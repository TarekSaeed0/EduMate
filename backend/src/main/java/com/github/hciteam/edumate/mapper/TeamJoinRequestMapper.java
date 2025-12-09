package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.Student;
import com.github.hciteam.edumate.model.Team;
import com.github.hciteam.edumate.model.TeamJoinRequest;
import com.github.hciteam.edumate.repository.StudentRepository;
import com.github.hciteam.edumate.repository.TeamRepository;
import com.github.hciteam.edumate.dto.StudentDTO;
import com.github.hciteam.edumate.dto.TeamDTO;
import com.github.hciteam.edumate.dto.TeamJoinRequestDTO;
import com.github.hciteam.edumate.exception.CourseOfferingNotFoundException;

@Mapper(componentModel = "spring",
		uses = {TeamMapper.class, StudentMapper.class})
public abstract class TeamJoinRequestMapper {
	@Autowired
	protected TeamRepository teamRepository;
	@Autowired
	protected StudentRepository studentRepository;

	public abstract TeamJoinRequestDTO toDTO(TeamJoinRequest request);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "team", target = "team", qualifiedByName = "mapTeam")
	@Mapping(source = "student", target = "student",
			qualifiedByName = "mapStudent")
	@Mapping(target = "status", constant = "PENDING")
	public abstract TeamJoinRequest toEntity(TeamJoinRequestDTO requestDTO);

	@Named("mapTeam")
	protected Team mapTeam(TeamDTO teamDTO) {
		return teamRepository.findById(teamDTO.getId())
				.orElseThrow(() -> new CourseOfferingNotFoundException());
	}

	@Named("mapStudent")
	protected Student mapStudent(StudentDTO studentDTO) {
		return studentRepository.findById(studentDTO.getId())
				.orElseThrow(() -> new CourseOfferingNotFoundException());
	}
}
