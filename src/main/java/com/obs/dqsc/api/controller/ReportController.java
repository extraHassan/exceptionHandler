package com.obs.dqsc.api.controller;

import com.obs.dqsc.api.exception.functional.InvalidInputException;
import com.obs.dqsc.api.exception.functional.ResourceNotFoundException;
import com.obs.dqsc.api.service.ReportServiceImpl;
import com.obs.dqsc.api.util.converter.ReportConverter;
import com.obs.dqsc.api.util.enums.CustomerAttributes;
import com.obs.dqsc.api.util.enums.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


/**
 * @author BJTL9958
 * @since 11-09-2021
 */
@RestController
@RequestMapping("api/v1/report")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "report-controller", description = "Controller managing operations related to reports")
public class ReportController {
    public static final String NO_EXTRACTION_DATE_VALUE = "No extraction date value was provided";
    public static final String NOT_A_VALID_REPORT_TYPE = "The provided Type is not a valid Report Type";
    private final ReportServiceImpl reportService;
    private static final String REPORT_TYPE="reportType";
    private static final String EXTRACTION_DATE = "extractionDate";
    private static final String REPORTS = "Reports";

    @GetMapping(value = "/report-file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Generate the report as Excel file ")
    public ResponseEntity<ByteArrayResource> getReportFile(@RequestParam MultiValueMap<String, String> params) throws InvalidInputException, DateTimeParseException, IOException {
            var extractionDateList = Optional.ofNullable(params.get(EXTRACTION_DATE)).orElseThrow(() -> new InvalidInputException(NO_EXTRACTION_DATE_VALUE));
            LocalDate extractionDate = null;

            if (!extractionDateList.isEmpty()) {
                extractionDate = LocalDate.parse(extractionDateList.get(0));
            }
            var result = Optional.ofNullable(reportService.findReportByCustomerAndDate(
                    Optional.ofNullable(params.get(CustomerAttributes.ENTITY_PERIMETER.value())).orElse(List.of()),
                    Optional.ofNullable(params.get(CustomerAttributes.NAME.value())).orElse(List.of()),
                    Optional.ofNullable(params.get(CustomerAttributes.IC_01.value())).orElse(List.of()),
                    Optional.ofNullable(params.get(CustomerAttributes.SIREN.value())).orElse(List.of()),
                    extractionDate,
                    Optional.of(Type.valueOf(params.get(REPORT_TYPE).get(0))).orElseThrow(() -> new InvalidInputException(NOT_A_VALID_REPORT_TYPE))

            ));

            if(result.isPresent())  return new ResponseEntity<>(ReportConverter.convertToCSV(result.get()), HttpStatus.CREATED);
            else throw(new ResourceNotFoundException(REPORTS));

    }

}
