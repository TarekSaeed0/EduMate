package com.github.hciteam.edumate.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.specification.FAQSpecifications;
import com.github.hciteam.edumate.repository.FAQCategoryRepository;
import com.github.hciteam.edumate.repository.FAQRepository;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.exception.FAQNotFoundException;
import com.github.hciteam.edumate.dto.FAQDTO;
import com.github.hciteam.edumate.mapper.FAQMapper;

@Service
public class FAQService {
	private final FAQRepository faqRepository;
	private final FAQCategoryRepository faqCategoryRepository;
	private final FAQMapper faqMapper;

	public FAQService(FAQRepository faqRepository,
			FAQCategoryRepository faqCategoryRepository, FAQMapper faqMapper) {
		this.faqRepository = faqRepository;
		this.faqCategoryRepository = faqCategoryRepository;
		this.faqMapper = faqMapper;
	}

	public List<FAQDTO> getFAQs(String question, String answer,
			List<String> categories) {
		Specification<FAQ> specification = Specification.unrestricted();

		if (question != null) {
			specification =
					specification.and(FAQSpecifications.questionContains(question));
		}

		if (answer != null) {
			specification =
					specification.and(FAQSpecifications.answerContains(question));
		}

		if (categories != null) {
			specification = specification
					.and(FAQSpecifications.hasAllCategoriesNames(categories));
		}

		return faqRepository.findAll(specification).stream().map(faqMapper::toDTO)
				.toList();
	}

	public FAQDTO createFAQ(FAQDTO faqDTO) {
		FAQ faq = faqMapper.toEntity(faqDTO);

		return faqMapper.toDTO(faqRepository.save(faq));
	}

	public FAQDTO getFAQ(Long faqId) {
		return faqRepository.findById(faqId).map(faqMapper::toDTO)
				.orElseThrow(() -> new FAQNotFoundException());
	}

	public FAQDTO updateFAQ(Long faqId, FAQDTO faqDTO) {
		FAQ faq = faqRepository.findById(faqId).map(existingFAQ -> {
			faqMapper.updateFAQFromDTO(faqDTO, existingFAQ);
			return existingFAQ;
		}).orElseThrow(() -> new FAQNotFoundException());

		return faqMapper.toDTO(faqRepository.save(faq));
	}

	public void deleteFAQ(Long faqId) {
		if (!faqRepository.existsById(faqId)) {
			throw new FAQNotFoundException();
		}

		faqRepository.deleteById(faqId);
	}

	public List<String> getCategories() {
		return faqCategoryRepository.findAll().stream()
				.map(category -> category.getName()).toList();
	}
}
