package com.obs.dqsc.api.repository.mongo_template;

import com.mongodb.DBObject;
import com.obs.dqsc.api.domain.document.embedded.Customer;
import com.obs.dqsc.api.repository.mongo_template.helper.DocumentToCustomerMapper;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    public static final String EXTRACTION_DATE = "extractionDate";
    private final MongoTemplate template;
    private static final String COLLECTION = "kpi";
    private static final String CUSTOMER = "customer";

    @Override
    public List<Customer> findCustomerByAttributes(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate) {

        Aggregation aggregation = newAggregation(
                match(new Criteria()
                        .orOperator(
                                Criteria.where("customer.name").in(name),
                                Criteria.where("customer.siren").in(siren),
                                Criteria.where("customer.ic01").in(ic01),
                                Criteria.where("customer.entityPerimeter").in(entityPerimeter),
                                Criteria.where("customer.enterpriseId").in(enterpriseId)
                        )
                ),
                match(Criteria.where(EXTRACTION_DATE).is(extractionDate)),
                project(CUSTOMER)

        );
        return template.aggregate(aggregation, COLLECTION, DBObject.class)
                .getMappedResults()
                .stream()
                .filter(Objects::nonNull)
                .map(dbObject -> dbObject.get(CUSTOMER))
                .filter(Objects::nonNull)
                .map(Document.class::cast)
                .map(DocumentToCustomerMapper::getCustomer)
                .distinct()
                .toList();
    }

    @Override
    public List<Customer> findAllCustomers() {
        return template.aggregate(newAggregation(project(CUSTOMER)), COLLECTION, DBObject.class)
                .getMappedResults()
                .stream()
                .filter(Objects::nonNull)
                .map(dbObject -> dbObject.get(CUSTOMER))
                .filter(Objects::nonNull)
                .map(Document.class::cast)
                .map(DocumentToCustomerMapper::getCustomer)
                .distinct()
                .toList();
    }

    @Override
    public List<Customer> findAllCustomersByExtractionDate(LocalDate extractionDate) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(EXTRACTION_DATE).is(extractionDate)),
                project(CUSTOMER)

        );

        return template.aggregate(aggregation, COLLECTION, DBObject.class)
                .getMappedResults()
                .stream()
                .filter(Objects::nonNull)
                .map(dbObject -> dbObject.get(CUSTOMER))
                .filter(Objects::nonNull)
                .map(Document.class::cast)
                .map(DocumentToCustomerMapper::getCustomer)
                .distinct()
                .toList();
    }
}


