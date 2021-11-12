package com.obs.dqsc.api.repository.mongo_template;

import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Repository
@AllArgsConstructor
public class DateRepositoryImpl implements DateRepository {
    public static final String EXTRACTION_DATE = "extractionDate";
    private final MongoTemplate template;
    private static final String COLLECTION = "kpi";


    @Override
    public List<LocalDate> findDistinctExtractionDates() {
        return template.aggregate(newAggregation(project(EXTRACTION_DATE).andExclude("_id")), COLLECTION, DBObject.class)
                .getMappedResults()
                .stream()
                .filter(Objects::nonNull)
                .map(dbObject -> dbObject.get(EXTRACTION_DATE))
                .filter(Objects::nonNull)
                .map(date -> ((Date) date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .distinct()
                .sorted((o1, o2) -> {
                    if (o1.isBefore(o2)) return 1;
                    else if (o1.isEqual(o2)) return 0;
                    else
                        return -1;
                })
                .toList();
    }
}
