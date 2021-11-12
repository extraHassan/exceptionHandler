package com.obs.dqsc.api.repository.mongo_template.helper;

import com.obs.dqsc.api.domain.document.embedded.Customer;
import com.obs.dqsc.api.util.enums.CustomerAttributes;
import org.bson.Document;

import java.util.Optional;

public interface DocumentToCustomerMapper {
    String NULL = "null";

    static Customer getCustomer(Document document) {
        var customer = new Customer();
        customer.setSiren(Optional.ofNullable((String) document.get(CustomerAttributes.SIREN.value())).orElse(NULL));
        customer.setEnterpriseId(Optional.ofNullable((String) document.get(CustomerAttributes.ENTERPRISE_ID.value())).orElse(NULL));
        customer.setEntityPerimeter(Optional.ofNullable((String) document.get(CustomerAttributes.ENTITY_PERIMETER.value())).orElse(NULL));
        customer.setName(Optional.ofNullable((String) document.get(CustomerAttributes.NAME.value())).orElse(NULL));
        customer.setIc01(Optional.ofNullable((String) document.get(CustomerAttributes.IC_01.value())).orElse(NULL));
        return customer;
    }
}

