package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.github.hciteam.edumate.model.FAQDTO;
import org.springframework.http.ResponseEntity;
import com.github.hciteam.edumate.service.FAQService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {
	FAQService faqService;

	public FAQController(FAQService faqService) {
		this.faqService = faqService;
	}

	@GetMapping
	public ResponseEntity<List<FAQDTO>> getFAQs(
			@RequestParam(required = false) String question,
			@RequestParam(required = false) String answer,
			@RequestParam(required = false) List<String> categories) {
		return ResponseEntity.ok(faqService.getFAQs(question, answer, categories));
	}

	@GetMapping("/{id}")
	public ResponseEntity<FAQDTO> getFAQ(@PathVariable Long id) {
		return ResponseEntity.ok(faqService.getFAQ(id));
	}
}
