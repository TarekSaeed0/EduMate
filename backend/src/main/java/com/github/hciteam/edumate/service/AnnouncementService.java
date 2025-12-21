package com.github.hciteam.edumate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.dto.AnnouncementDTO;
import com.github.hciteam.edumate.exception.AnnouncementNotFoundException;
import com.github.hciteam.edumate.exception.AnnouncementScopeNotFoundException;
import com.github.hciteam.edumate.mapper.AnnouncementMapper;
import com.github.hciteam.edumate.model.Announcement;
import com.github.hciteam.edumate.model.AnnouncementScope;
import com.github.hciteam.edumate.repository.AnnouncementRepository;
import com.github.hciteam.edumate.repository.AnnouncementScopeRepository;
import com.github.hciteam.edumate.specification.AnnouncementSpecificationFactory;

@Service
public class AnnouncementService {
	private AnnouncementRepository announcementRepository;
	private AnnouncementMapper announcementMapper;
	private AnnouncementScopeService scopeService;
	private Map<String, AnnouncementSpecificationFactory> specificationFactories =
			new HashMap<>();


	public AnnouncementService(AnnouncementRepository announcementRepository,
			AnnouncementMapper announcementMapper,
			AnnouncementScopeService scopeService,
			List<AnnouncementSpecificationFactory> specificationFactories) {
		this.announcementRepository = announcementRepository;
		this.announcementMapper = announcementMapper;
		this.scopeService = scopeService;

		for (AnnouncementSpecificationFactory specificationFactory : specificationFactories) {
			this.specificationFactories.put(specificationFactory.getScopeType(),
					specificationFactory);
		}
	}

	private Specification<Announcement> ofStudent(Long studentId) {
		Specification<Announcement> specification = Specification.unrestricted();

		for (AnnouncementSpecificationFactory specificationFactory : specificationFactories
				.values()) {
			specification =
					specification.or(specificationFactory.ofStudent(studentId));
		}

		return specification;
	}

	public List<AnnouncementDTO> getAnnouncements(Long studentId) {
		Specification<Announcement> specification = Specification.unrestricted();

		if (studentId != null) {
			specification = specification.and(ofStudent(studentId));
		}

		return announcementRepository.findAll(specification).stream()
				.map(announcement -> {
					AnnouncementScopeRepository scopeRepository =
							scopeService.getScopeRepository(announcement.getScopeType());

					announcement
							.setScope(
									scopeRepository.findScopeById(announcement.getScopeId())
											.map(scope -> (AnnouncementScope) scope)
											.orElseThrow(() -> new AnnouncementScopeNotFoundException(
													announcement.getScopeType(),
													announcement.getScopeId())));

					return announcement;
				}).map(announcementMapper::toDTO).toList();
	}

	public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
		Announcement announcement = announcementMapper.toEntity(announcementDTO);

		return announcementMapper.toDTO(announcementRepository.save(announcement));
	}

	public AnnouncementDTO getAnnouncement(Long announcementId) {
		return announcementRepository.findById(announcementId)
				.map(announcement -> announcementMapper.toDTO(announcement))
				.orElseThrow(() -> new AnnouncementNotFoundException(announcementId));
	}

	public AnnouncementDTO updateAnnouncement(Long announcementId,
			AnnouncementDTO announcementDTO) {
		Announcement announcement = announcementRepository.findById(announcementId)
				.map(existingAnnouncement -> {
					announcementMapper.updateEntityFromDTO(announcementDTO,
							existingAnnouncement);
					return existingAnnouncement;
				}).orElseThrow(() -> new AnnouncementNotFoundException(announcementId));

		return announcementMapper.toDTO(announcementRepository.save(announcement));
	}

	public void deleteAnnouncement(Long announcementId) {
		if (!announcementRepository.existsById(announcementId)) {
			throw new AnnouncementNotFoundException(announcementId);
		}

		announcementRepository.deleteById(announcementId);
	}
}
