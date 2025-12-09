package com.github.hciteam.edumate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URI;
import java.util.List;
import com.github.hciteam.edumate.dto.FAQDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.github.hciteam.edumate.service.FAQService;
import com.github.hciteam.edumate.validation.ValidationGroups;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {
	private final FAQService faqService;

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

	@PostMapping
	public ResponseEntity<FAQDTO> createFAQ(
			@Validated(ValidationGroups.Create.class) @RequestBody FAQDTO faqDTO) {
		FAQDTO createdFAQ = faqService.createFAQ(faqDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{faqId}").buildAndExpand(createdFAQ.getId()).toUri();

		return ResponseEntity.created(location).body(createdFAQ);
	}

	@GetMapping("/{faqId}")
	public ResponseEntity<FAQDTO> getFAQ(@PathVariable Long faqId) {
		return ResponseEntity.ok(faqService.getFAQ(faqId));
	}

	@PutMapping("/{faqId}")
	public ResponseEntity<FAQDTO> updateFAQ(@PathVariable Long faqId,
			@Validated(ValidationGroups.Update.class) @RequestBody FAQDTO faqDTO) {
		return ResponseEntity.ok(faqService.updateFAQ(faqId, faqDTO));
	}

	@GetMapping("/categories")
	public ResponseEntity<List<String>> getCategories() {
		return ResponseEntity.ok(faqService.getCategories());
	}

	@DeleteMapping("/{faqId}")
	public ResponseEntity<Void> deleteFAQ(@PathVariable Long faqId) {
		faqService.deleteFAQ(faqId);

		return ResponseEntity.noContent().build();
	}
}
