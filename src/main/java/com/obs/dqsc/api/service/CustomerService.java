package com.obs.dqsc.api.service;

import com.obs.dqsc.api.domain.document.embedded.Customer;

import java.time.LocalDate;
import java.util.List;

/**
 * @author KDFL4681
 * @since 11-09-2021
 */
public interface CustomerService {
    List<Customer> findCustomerByAttributes(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate);

    List<Customer> findAllCustomers();

    List<Customer> findAllCustomersByExtractionDate(LocalDate extractionDate);
}
