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
import com.github.hciteam.edumate.dto.TimePeriodDTO;
import com.github.hciteam.edumate.service.TimePeriodService;
import com.github.hciteam.edumate.validation.ValidationGroups;

@RestController
@RequestMapping("/api/time-periods")
public class TimePeriodController {
	private final TimePeriodService periodService;

	public TimePeriodController(TimePeriodService periodService) {
		this.periodService = periodService;
	}

	@GetMapping
	public ResponseEntity<List<TimePeriodDTO>> getPeriods() {
		return ResponseEntity.ok(periodService.getPeriods());
	}

	@PostMapping
	public ResponseEntity<TimePeriodDTO> createPeriod(
			@Validated(ValidationGroups.Create.class) @RequestBody TimePeriodDTO periodDTO) {
		TimePeriodDTO createdPeriod = periodService.createPeriod(periodDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{periodId}").buildAndExpand(createdPeriod.getId()).toUri();

		return ResponseEntity.created(location).body(createdPeriod);
	}

	@GetMapping("/{periodId}")
	public ResponseEntity<TimePeriodDTO> getPeriod(@PathVariable Long periodId) {
		return ResponseEntity.ok(periodService.getPeriod(periodId));
	}

	@PutMapping("/{periodId}")
	public ResponseEntity<TimePeriodDTO> updatePeriod(@PathVariable Long periodId,
			@Validated(ValidationGroups.Update.class) @RequestBody TimePeriodDTO periodDTO) {
		return ResponseEntity.ok(periodService.updatePeriod(periodId, periodDTO));
	}

	@DeleteMapping("/{periodId}")
	public ResponseEntity<Void> deletePeriod(@PathVariable Long periodId) {
		periodService.deletePeriod(periodId);

		return ResponseEntity.noContent().build();
	}
}
