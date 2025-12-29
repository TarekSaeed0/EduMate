package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.hciteam.edumate.model.FAQCategory;
import com.github.hciteam.edumate.repository.FAQCategoryRepository;

@Mapper(componentModel = "spring")
public abstract class FAQCategoryMapper {
	@Autowired
	protected FAQCategoryRepository categoryRepository;

	String toString(FAQCategory category) {
		return category.getName();
	}

	FAQCategory toEntity(String categoryName) {
		return categoryRepository.findByName(categoryName)
				.orElseGet(() -> new FAQCategory(categoryName));
	}
}
