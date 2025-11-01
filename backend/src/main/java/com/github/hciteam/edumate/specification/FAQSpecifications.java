package com.github.hciteam.edumate.specification;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.github.hciteam.edumate.entity.FAQ;

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

			query.distinct(true);
			query.groupBy(root.get("id"));
			query.having(criteriaBuilder.equal(
					criteriaBuilder.countDistinct(root.join("categories").get("name")),
					categoryNames.size()));

			return root.join("categories").get("name").in(categoryNames);
		};
	}
}
