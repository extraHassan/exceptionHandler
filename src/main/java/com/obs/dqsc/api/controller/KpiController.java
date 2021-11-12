package com.obs.dqsc.api.controller;

import com.obs.dqsc.api.domain.dto.KpiDTO;
import com.obs.dqsc.api.exception.functional.InvalidInputException;
import com.obs.dqsc.api.exception.functional.ResourceNotFoundException;
import com.obs.dqsc.api.service.KpiService;
import com.obs.dqsc.api.util.enums.CustomerAttributes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * @author KDFL4681
 * @since 11-09-2021
 */
@RestController
@RequestMapping("api/v1/kpi")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "kpi-controller", description = "Controller managing operations related to kpis")
public class KpiController {
    public static final String NO_EXTRACTION_DATE_VALUE = "No extraction date value was provided";
    private final KpiService kpiService;
    private static final String EXTRACTION_DATE = "extractionDate";
    private static final String KPIS = "Kpis";

    @GetMapping(value = "/line-kpi", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find Kpis by one or more customer property")
    public ResponseEntity<List<KpiDTO>> findKPILineByCustomer(@RequestParam MultiValueMap<String, String> params) throws InvalidInputException , DateTimeParseException {
            var extractionDateList = Optional.ofNullable(params.get(EXTRACTION_DATE)).orElseThrow(() -> new InvalidInputException(NO_EXTRACTION_DATE_VALUE));
            LocalDate extractionDate = null;

            if (!extractionDateList.isEmpty()) {
                extractionDate = LocalDate.parse(extractionDateList.get(0));
            }
            var linesKpi = Optional.ofNullable(
                    kpiService.findKPILineByCustomer(
                            Optional.ofNullable(params.get(CustomerAttributes.ENTITY_PERIMETER.value())).orElse(List.of()),
                            Optional.ofNullable(params.get(CustomerAttributes.NAME.value())).orElse(List.of()),
                            Optional.ofNullable(params.get(CustomerAttributes.IC_01.value())).orElse(List.of()),
                            Optional.ofNullable(params.get(CustomerAttributes.SIREN.value())).orElse(List.of()),
                            Optional.ofNullable(params.get(CustomerAttributes.ENTERPRISE_ID.value())).orElse(List.of()),
                            extractionDate
                    )
            );
            return linesKpi.map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException(KPIS));
    }


}
