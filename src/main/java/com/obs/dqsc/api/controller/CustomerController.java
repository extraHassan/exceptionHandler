package com.obs.dqsc.api.controller;

import com.obs.dqsc.api.domain.document.embedded.Customer;
import com.obs.dqsc.api.exception.functional.InvalidInputException;
import com.obs.dqsc.api.exception.functional.ResourceNotFoundException;
import com.obs.dqsc.api.service.CustomerService;
import com.obs.dqsc.api.util.enums.CustomerAttributes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@RequestMapping("api/v1/customers")
@AllArgsConstructor
@Controller
@CrossOrigin
@Tag(name = "customer-controller", description = "Controller managing operations related to customers ")
public class CustomerController {
    public static final String NO_EXTRACTION_DATE_VALUE = "No extraction date value was provided";
    public static final String COSTUMERS = "Costumers";
    private final CustomerService customerService;
    private static final String EXTRACTION_DATE = "extractionDate";
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find all customers")
    public ResponseEntity<List<Customer>> findAllCustomers() {
        var customers = Optional.of(customerService.findAllCustomers());
        return customers.map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException(COSTUMERS));
    }


    @GetMapping(value = "/extraction-date", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find  customers by extraction date")
    public ResponseEntity<List<Customer>> findAllCustomersByExtractionDate(@RequestParam MultiValueMap<String, String> params) throws InvalidInputException , DateTimeParseException {
            var extractionDateList = Optional.ofNullable(params.get(EXTRACTION_DATE)).orElseThrow(() -> new InvalidInputException(NO_EXTRACTION_DATE_VALUE));
            if (!extractionDateList.isEmpty()) {
                var extractionDate = LocalDate.parse(extractionDateList.get(0));
                var customers = Optional.of(customerService.findAllCustomersByExtractionDate(extractionDate));
                return customers.map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException(COSTUMERS));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping(value = "/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find customers by one or more customer property")
    public ResponseEntity<List<Customer>> findCustomerByAttributes(@RequestParam MultiValueMap<String, String> params) throws InvalidInputException ,DateTimeParseException {
        var extractionDateList = Optional.ofNullable(params.get(EXTRACTION_DATE)).orElseThrow(() -> new InvalidInputException(NO_EXTRACTION_DATE_VALUE));
            if (!extractionDateList.isEmpty()) {
                var extractionDate = LocalDate.parse(extractionDateList.get(0));
                var customers = Optional.ofNullable(
                        customerService.findCustomerByAttributes(
                                Optional.ofNullable(params.get(CustomerAttributes.ENTITY_PERIMETER.value())).orElse(List.of()),
                                Optional.ofNullable(params.get(CustomerAttributes.NAME.value())).orElse(List.of()),
                                Optional.ofNullable(params.get(CustomerAttributes.IC_01.value())).orElse(List.of()),
                                Optional.ofNullable(params.get(CustomerAttributes.SIREN.value())).orElse(List.of()),
                                Optional.ofNullable(params.get(CustomerAttributes.ENTERPRISE_ID.value())).orElse(List.of()),
                                extractionDate
                        )
                );

                return customers.map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException(COSTUMERS));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

    }

}
