package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.University;
import com.github.hciteam.edumate.exception.UniversityNotFoundException;
import com.github.hciteam.edumate.exception.UniversityAlreadyExistsException;
import com.github.hciteam.edumate.mapper.UniversityMapper;
import com.github.hciteam.edumate.dto.UniversityDTO;
import com.github.hciteam.edumate.repository.UniversityRepository;

@Service
public class UniversityService {
	private final UniversityRepository universityRepository;
	private final UniversityMapper universityMapper;

	public UniversityService(UniversityRepository universityRepository,
			UniversityMapper universityMapper) {
		this.universityRepository = universityRepository;
		this.universityMapper = universityMapper;
	}

	public List<UniversityDTO> getUniversitys() {
		return universityRepository.findAll().stream().map(universityMapper::toDTO)
				.toList();
	}

	public UniversityDTO createUniversity(UniversityDTO universityDTO) {
		if (universityRepository.existsByName(universityDTO.getName())) {
			throw new UniversityAlreadyExistsException();
		}

		University university = universityMapper.toEntity(universityDTO);

		return universityMapper.toDTO(universityRepository.save(university));
	}

	public UniversityDTO getUniversity(Long universityId) {
		return universityRepository.findById(universityId)
				.map(university -> universityMapper.toDTO(university))
				.orElseThrow(() -> new UniversityNotFoundException(universityId));
	}

	public UniversityDTO updateUniversity(Long universityId, UniversityDTO universityDTO) {
		University university = universityRepository.findById(universityId).map(existingUniversity -> {
			universityMapper.updateEntityFromDTO(universityDTO, existingUniversity);
			return existingUniversity;
		}).orElseThrow(() -> new UniversityNotFoundException(universityId));

		return universityMapper.toDTO(universityRepository.save(university));
	}

	public void deleteUniversity(Long universityId) {
		if (!universityRepository.existsById(universityId)) {
			throw new UniversityNotFoundException(universityId);
		}

		universityRepository.deleteById(universityId);
	}
}
