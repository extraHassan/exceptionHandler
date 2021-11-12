package com.obs.dqsc.api.controller;

import com.obs.dqsc.api.exception.functional.ResourceNotFoundException;
import com.obs.dqsc.api.service.DateServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author KDFL4681
 * @since 11-09-2021
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/date")
@Controller
@CrossOrigin
@Tag(name = "date-controller", description = "Controller managing operations related to  dates")
public class DateController {
    private final DateServiceImpl dateService;
    private static final String DATES = "Dates";

    @GetMapping(value = "/extraction-date", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find all Distinct extraction Dates")
    public ResponseEntity<List<LocalDate>> findDistinctExtractionDates() {
            var extractionDates = Optional.ofNullable(dateService.findDistinctExtractionDates());
            return extractionDates.map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException(DATES));
    }
}
