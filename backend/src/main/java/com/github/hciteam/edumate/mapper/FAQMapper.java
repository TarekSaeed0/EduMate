package com.github.hciteam.edumate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.dto.FAQDTO;

@Mapper(componentModel = "spring", uses = {FAQCategoryMapper.class})
public interface FAQMapper {
	FAQDTO toDTO(FAQ faq);

	@Mapping(target = "id", ignore = true)
	FAQ toEntity(FAQDTO faqDTO);

	@Mapping(target = "id", ignore = true)
	void updateEntityFromDTO(FAQDTO faqDTO, @MappingTarget FAQ faq);
}
