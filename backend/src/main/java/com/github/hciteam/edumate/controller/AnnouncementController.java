package com.github.hciteam.edumate.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.AnnouncementDTO;
import com.github.hciteam.edumate.service.AnnouncementService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
	private final AnnouncementService announcementService;

	public AnnouncementController(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	@GetMapping
	public ResponseEntity<List<AnnouncementDTO>> getAnnouncements(
			@RequestParam(required = false) Long studentId) {
		return ResponseEntity.ok(announcementService.getAnnouncements(studentId));
	}

	@PostMapping
	public ResponseEntity<AnnouncementDTO> createAnnouncement(
			@Validated(ValidationGroups.Create.class) @RequestBody AnnouncementDTO announcementDTO) {
		AnnouncementDTO createdAnnouncement =
				announcementService.createAnnouncement(announcementDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{announcementId}").buildAndExpand(createdAnnouncement.getId())
				.toUri();

		return ResponseEntity.created(location).body(createdAnnouncement);
	}

	@GetMapping("/{announcementId}")
	public ResponseEntity<AnnouncementDTO> getAnnouncement(
			@PathVariable Long announcementId) {
		return ResponseEntity
				.ok(announcementService.getAnnouncement(announcementId));
	}

	@PutMapping("/{announcementId}")
	public ResponseEntity<AnnouncementDTO> updateAnnouncement(
			@PathVariable Long announcementId,
			@Validated(ValidationGroups.Update.class) @RequestBody AnnouncementDTO announcementDTO) {
		return ResponseEntity.ok(announcementService
				.updateAnnouncement(announcementId, announcementDTO));
	}

	@DeleteMapping("/{announcementId}")
	public ResponseEntity<Void> deleteAnnouncement(
			@PathVariable Long announcementId) {
		announcementService.deleteAnnouncement(announcementId);

		return ResponseEntity.noContent().build();
	}
}
