package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import com.github.hciteam.edumate.model.FAQCategory;
import com.github.hciteam.edumate.repository.FAQCategoryRepository;

@Mapper(componentModel = "spring")
public abstract class FAQCategoryMapper {
	protected final FAQCategoryRepository categoryRepository;

	protected FAQCategoryMapper(FAQCategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	String toString(FAQCategory category) {
		return category.getName();
	}

	FAQCategory toEntity(String categoryName) {
		return categoryRepository.findByName(categoryName)
				.orElseGet(() -> FAQCategory.builder().name(categoryName).build());
	}
}
