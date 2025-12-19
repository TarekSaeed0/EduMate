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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.hciteam.edumate.dto.TimeSlotDTO;
import com.github.hciteam.edumate.service.TimeSlotService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/time-slots")
public class TimeSlotController {
	private final TimeSlotService slotService;

	public TimeSlotController(TimeSlotService slotService) {
		this.slotService = slotService;
	}

	@GetMapping
	public ResponseEntity<List<TimeSlotDTO>> getSlots() {
		return ResponseEntity.ok(slotService.getSlots());
	}

	@PostMapping
	public ResponseEntity<TimeSlotDTO> createSlot(
			@Validated(ValidationGroups.Create.class) @RequestBody TimeSlotDTO slotDTO) {
		TimeSlotDTO createdSlot = slotService.createSlot(slotDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{slotId}").buildAndExpand(createdSlot.getId()).toUri();

		return ResponseEntity.created(location).body(createdSlot);
	}

	@GetMapping("/{slotId}")
	public ResponseEntity<TimeSlotDTO> getSlot(@PathVariable Long slotId) {
		return ResponseEntity.ok(slotService.getSlot(slotId));
	}

	@PutMapping("/{slotId}")
	public ResponseEntity<TimeSlotDTO> updateSlot(@PathVariable Long slotId,
			@Validated(ValidationGroups.Update.class) @RequestBody TimeSlotDTO slotDTO) {
		return ResponseEntity.ok(slotService.updateSlot(slotId, slotDTO));
	}

	@DeleteMapping("/{slotId}")
	public ResponseEntity<Void> deleteSlot(@PathVariable Long slotId) {
		slotService.deleteSlot(slotId);

		return ResponseEntity.noContent().build();
	}
}
