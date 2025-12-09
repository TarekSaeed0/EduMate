package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Semester;
import com.github.hciteam.edumate.exception.SemesterAlreadyExistsException;
import com.github.hciteam.edumate.exception.SemesterNotFoundException;
import com.github.hciteam.edumate.mapper.SemesterMapper;
import com.github.hciteam.edumate.dto.SemesterDTO;
import com.github.hciteam.edumate.repository.SemesterRepository;

@Service
public class SemesterService {
	private final SemesterRepository semesterRepository;
	private final SemesterMapper semesterMapper;

	public SemesterService(SemesterRepository semesterRepository,
			SemesterMapper semesterMapper) {
		this.semesterRepository = semesterRepository;
		this.semesterMapper = semesterMapper;
	}

	public List<SemesterDTO> getSemesters() {
		return semesterRepository.findAll().stream().map(semesterMapper::toDTO)
				.toList();
	}

	public SemesterDTO createSemester(SemesterDTO semesterDTO) {
		if (semesterRepository.existsByTermAndYear(semesterDTO.getTerm(),
				semesterDTO.getYear())) {
			throw new SemesterAlreadyExistsException();
		}

		Semester semester = semesterMapper.toEntity(semesterDTO);

		return semesterMapper.toDTO(semesterRepository.save(semester));
	}

	public SemesterDTO getSemester(Long semesterId) {
		return semesterRepository.findById(semesterId).map(semesterMapper::toDTO)
				.orElseThrow(() -> new SemesterNotFoundException());
	}

	public SemesterDTO updateSemester(Long semesterId, SemesterDTO semesterDTO) {
		Semester semester =
				semesterRepository.findById(semesterId).map(existingSemester -> {
					semesterMapper.updateEntityFromDTO(semesterDTO, existingSemester);
					return existingSemester;
				}).orElseThrow(() -> new SemesterNotFoundException());

		return semesterMapper.toDTO(semesterRepository.save(semester));
	}

	public void deleteSemester(Long semesterId) {
		if (!semesterRepository.existsById(semesterId)) {
			throw new SemesterNotFoundException();
		}

		semesterRepository.deleteById(semesterId);
	}

}
