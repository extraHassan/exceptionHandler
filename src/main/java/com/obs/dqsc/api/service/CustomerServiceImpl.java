package com.obs.dqsc.api.service;

import com.obs.dqsc.api.domain.document.embedded.Customer;
import com.obs.dqsc.api.repository.mongo_template.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author KDFL4681
 * @since 11-09-2021
 */
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * @param entityPerimeter entity Perimeter
     * @param name name of the sub entity Perimeter
     * @param ic01 unique id
     * @param siren Business Register Identification System
     * @param enterpriseId id of the enterprise
     * @param extractionDate date of extraction from EDH
     * @return return list of customers
     */
    @Override
    public List<Customer> findCustomerByAttributes(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate) {
        return customerRepository.findCustomerByAttributes(entityPerimeter, name, ic01, siren, enterpriseId, extractionDate);
    }

    /**
     * @return List of customers
     */
    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAllCustomers();
    }

    /**
     * @param extractionDate date of extraction from EDH
     * @return list of customers
     */
    @Override
    public List<Customer> findAllCustomersByExtractionDate(LocalDate extractionDate) {
        return customerRepository.findAllCustomersByExtractionDate(extractionDate);
    }
}
