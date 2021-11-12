package com.obs.dqsc.api.repository;

import com.obs.dqsc.api.domain.document.Kpi;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author KDFL4681
 * @since 11-09-2021
 * @see org.springframework.data.mongodb.repository.MongoRepository
 */
@Repository
public interface KpiRepository extends MongoRepository<Kpi, ObjectId> {
    Optional<List<Kpi>> findAllByExtractionDate(LocalDate date);

    @Query("{ '$or' : [ {'customer.entityPerimeter' : { $in : ?0}}, {'customer.name' : { $in : ?1}} , {'customer.ic01': {$in : ?2}}, {'customer.siren': {$in : ?3}}, {'customer.ic01': {$in : ?4}}], 'extractionDate' : ?5}")
    Optional<List<Kpi>> findByAllCustomerAttributes(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate);
}
