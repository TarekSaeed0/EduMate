package com.github.hciteam.edumate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.github.hciteam.edumate.model.Announcement;
import com.github.hciteam.edumate.repository.AnnouncementRepository;
import com.github.hciteam.edumate.specification.AnnouncementSpecificationFactory;

@Service
public class AnnouncementService {
	private AnnouncementRepository announcementRepository;
	private Map<String, AnnouncementSpecificationFactory> specificationFactories =
			new HashMap<>();


	public AnnouncementService(AnnouncementRepository announcementRepository,
			List<AnnouncementSpecificationFactory> specificationFactories) {
		this.announcementRepository = announcementRepository;

		for (AnnouncementSpecificationFactory specificationFactory : specificationFactories) {
			this.specificationFactories.put(specificationFactory.getScopeType(),
					specificationFactory);
		}
	}

	private Specification<Announcement> ofUser(Long userId) {
		Specification<Announcement> specification = Specification.unrestricted();

		for (AnnouncementSpecificationFactory specificationFactory : specificationFactories
				.values()) {
			specification = specification.or(specificationFactory.ofUser(userId));
		}

		return specification;
	}

	public List<Announcement> getAnnouncements(Long userId) {
		Specification<Announcement> specification = Specification.unrestricted();

		if (userId != null) {
			specification = specification.and(ofUser(userId));
		}

		return announcementRepository.findAll(specification);
	}
}
