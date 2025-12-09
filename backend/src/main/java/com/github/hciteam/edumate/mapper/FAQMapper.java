package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.dto.FAQDTO;

@Mapper(componentModel = "spring", uses = {FAQCategoryMapper.class})
public interface FAQMapper {
	FAQDTO toDTO(FAQ faq);

	FAQ toEntity(FAQDTO faqDTO);

	void updateFAQFromDTO(FAQDTO faqDTO, @MappingTarget FAQ faq);
}
