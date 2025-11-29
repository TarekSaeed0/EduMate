package com.github.hciteam.edumate.specification;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.model.FAQ;
import com.github.hciteam.edumate.model.FAQCategory;
import jakarta.persistence.criteria.Join;

public class FAQSpecifications {

	public static Specification<FAQ> questionContains(String keyword) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(
				criteriaBuilder.lower(root.get("question")),
				"%" + keyword.toLowerCase() + "%");
	}

	public static Specification<FAQ> answerContains(String keyword) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(
				criteriaBuilder.lower(root.get("answer")),
				"%" + keyword.toLowerCase() + "%");
	}

	public static Specification<FAQ> hasAllCategoriesNames(
			List<String> categoryNames) {
		return (root, query, criteriaBuilder) -> {
			if (categoryNames.isEmpty()) {
				return criteriaBuilder.conjunction();
			}

			Join<FAQ, FAQCategory> categories = root.join("categories");

			query.groupBy(root.get("id"));
			query.having(criteriaBuilder.equal(
					criteriaBuilder.countDistinct(categories.get("name")),
					categoryNames.size()));

			return categories.get("name").in(categoryNames);
		};
	}
}
