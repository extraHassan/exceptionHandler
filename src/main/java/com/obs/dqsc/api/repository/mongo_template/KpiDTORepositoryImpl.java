package com.obs.dqsc.api.repository.mongo_template;

import com.obs.dqsc.api.domain.document.Kpi;
import com.obs.dqsc.api.domain.dto.KpiDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@AllArgsConstructor
@Repository
public class KpiDTORepositoryImpl implements KpiDTORepository{
    public static final String TOTAL_OVERALL_INSPECTED = "totalOverallInspected";
    public static final String EXTRACTION_DATE = "extractionDate";
    private final MongoTemplate template;
    private static final String TOTAL_DISCREPANCY ="totalDiscrepancy";

    @Override
    public List<KpiDTO> findKPILineByCustomer(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate) {
        MatchOperation matchStage;
        MatchOperation matchStageTrending;


        Criteria currentDateCriteria = Criteria.where(EXTRACTION_DATE).is(extractionDate);
        Criteria previousDateCriteria = Criteria.where(EXTRACTION_DATE).is(extractionDate.minusDays(1));


        // Init criteria
        Criteria initCriteria = new Criteria()
                .andOperator(
                        currentDateCriteria
                );
        Criteria initCriteriaTrending = new Criteria()
                .andOperator(
                        previousDateCriteria
                );


        // Criteria with params
        Criteria criteria = new Criteria().orOperator(
                        Criteria.where("customer.entityPerimeter").in(entityPerimeter),
                        Criteria.where("customer.name").in(name),
                        Criteria.where("customer.ic01").in(ic01),
                        Criteria.where("customer.siren").in(siren),
                        Criteria.where("customer.enterpriseId").in(enterpriseId)
                )
                .andOperator(
                        currentDateCriteria
                );
        Criteria criteriaTrending =new Criteria().orOperator(
                        Criteria.where("customer.entityPerimeter").in(entityPerimeter),
                        Criteria.where("customer.name").in(name),
                        Criteria.where("customer.ic01").in(ic01),
                        Criteria.where("customer.siren").in(siren),
                        Criteria.where("customer.enterpriseId").in(enterpriseId)
                )
                .andOperator(
                        previousDateCriteria
                );

        if (entityPerimeter.isEmpty() && name.isEmpty() && ic01.isEmpty() && siren.isEmpty() && enterpriseId.isEmpty()) {
            matchStage = Aggregation.match(initCriteria);
            matchStageTrending = Aggregation.match(initCriteriaTrending);
        } else {
            matchStage = Aggregation.match(criteria);
            matchStageTrending = Aggregation.match(criteriaTrending);
        }


        GroupOperation groupStage = Aggregation.group("type").sum("discrepancy").as(TOTAL_DISCREPANCY)
                .sum("totalInspected").as(TOTAL_OVERALL_INSPECTED);

        ProjectionOperation projectStage = project()
                .andExpression(TOTAL_DISCREPANCY).as(TOTAL_DISCREPANCY)
                .andExpression(TOTAL_OVERALL_INSPECTED).as(TOTAL_OVERALL_INSPECTED)
                .andExpression("_id").as("typeKPI");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupStage, projectStage);
        Aggregation aggregationTrending = Aggregation.newAggregation(matchStageTrending, groupStage, projectStage);

        var groupResults = template.aggregate(
                aggregation, Kpi.class, KpiDTO.class);
        var groupResultsTrending = template.aggregate(
                aggregationTrending, Kpi.class, KpiDTO.class);


        List<KpiDTO> lineKpiList = groupResults.getMappedResults();
        List<KpiDTO> lineKpiListTrending = groupResultsTrending.getMappedResults();

        lineKpiList.forEach(x -> x.processQualityLevel(lineKpiListTrending));

        return lineKpiList;
    }
}
