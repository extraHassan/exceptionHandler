package com.obs.dqsc.api.repository.mongo_template;

import com.obs.dqsc.api.domain.document.embedded.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository {
     List<Customer> findCustomerByAttributes(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate);
     List<Customer> findAllCustomers();
     List<Customer> findAllCustomersByExtractionDate(LocalDate extractionDate);
}


