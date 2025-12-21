package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.CourseMaterialDTO;
import com.github.hciteam.edumate.exception.CourseMaterialNotFoundException;
import com.github.hciteam.edumate.mapper.CourseMaterialMapper;
import com.github.hciteam.edumate.model.CourseMaterial;
import com.github.hciteam.edumate.repository.CourseMaterialRepository;
import com.github.hciteam.edumate.specification.CourseMaterialSpecifications;

@Service
public class CourseMaterialService {
	private final CourseMaterialRepository materialRepository;
	private final CourseMaterialMapper materialMapper;

	public CourseMaterialService(CourseMaterialRepository materialRepository,
			CourseMaterialMapper materialMapper) {
		this.materialRepository = materialRepository;
		this.materialMapper = materialMapper;
	}

	public List<CourseMaterialDTO> getMaterials(Long courseId) {
		Specification<CourseMaterial> specification = Specification.unrestricted();

		if (courseId != null) {
			specification =
					specification.and(CourseMaterialSpecifications.ofCourse(courseId));
		}

		return materialRepository.findAll(specification).stream()
				.map(materialMapper::toDTO).toList();
	}

	public CourseMaterialDTO createMaterial(CourseMaterialDTO materialDTO) {
		CourseMaterial material = materialMapper.toEntity(materialDTO);

		return materialMapper.toDTO(materialRepository.save(material));
	}

	public CourseMaterialDTO getMaterial(Long materialId) {
		return materialRepository.findById(materialId)
				.map(material -> materialMapper.toDTO(material))
				.orElseThrow(() -> new CourseMaterialNotFoundException(materialId));
	}

	public CourseMaterialDTO updateMaterial(Long materialId,
			CourseMaterialDTO materialDTO) {
		CourseMaterial material =
				materialRepository.findById(materialId).map(existingMaterial -> {
					materialMapper.updateEntityFromDTO(materialDTO, existingMaterial);
					return existingMaterial;
				}).orElseThrow(() -> new CourseMaterialNotFoundException(materialId));

		return materialMapper.toDTO(materialRepository.save(material));
	}

	public void deleteMaterial(Long materialId) {
		if (!materialRepository.existsById(materialId)) {
			throw new CourseMaterialNotFoundException(materialId);
		}

		materialRepository.deleteById(materialId);
	}
}
