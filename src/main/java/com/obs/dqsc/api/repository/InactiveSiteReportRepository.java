package com.obs.dqsc.api.repository;

import com.obs.dqsc.api.domain.document.ReportInactiveSite;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * @author
 * @since 11-09-2021
 * @see org.springframework.data.mongodb.repository.MongoRepository
 */
public interface InactiveSiteReportRepository extends MongoRepository<ReportInactiveSite, ObjectId> {
    @Query("{ '$or' : [ {'customer.entityPerimeter' : { $in : ?0}}, {'customer.name' : { $in : ?1}} , {'customer.ic01': {$in : ?2}}, {'customer.siren': {$in : ?3}}],  'extractionDate' : ?4}")
    Optional<List<ReportInactiveSite>> findByCustomerAndDate(List<String> entityPerimeter, List<String> name, List<String> ic01 , List<String> siren, LocalDate extractionDate);

    Optional<List<ReportInactiveSite>> findByExtractionDate(LocalDate extractionDate);
}
