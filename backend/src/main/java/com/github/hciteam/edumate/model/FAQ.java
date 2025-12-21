package com.github.hciteam.edumate.model;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "faqs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQ {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String question;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String answer;

	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "faqs_categories",
			joinColumns = @JoinColumn(name = "faq_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<FAQCategory> categories;
}
