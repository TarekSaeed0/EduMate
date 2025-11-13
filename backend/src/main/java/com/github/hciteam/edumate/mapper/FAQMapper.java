package com.github.hciteam.edumate.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import com.github.hciteam.edumate.entity.FAQCategory;
import com.github.hciteam.edumate.entity.FAQ;
import com.github.hciteam.edumate.model.FAQDTO;

@Mapper(componentModel = "spring")
public interface FAQMapper {
	FAQDTO toDTO(FAQ faq);

	default Set<String> mapCategoriesToStrings(Set<FAQCategory> categories) {
		return categories.stream().map(FAQCategory::getName)
				.collect(Collectors.toSet());
	}
}
