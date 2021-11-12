package com.obs.dqsc.api.domain.dto;

import com.obs.dqsc.api.util.enums.Type;
import lombok.*;

import java.util.List;

/**
 * @author
 * @since 11-09-2021
 */
@AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
@EqualsAndHashCode
public class KpiDTO {

    private Type typeKPI;
    private int totalDiscrepancy;
    private int totalOverallInspected;
    private double discrepancyLevel;
    private double dataQualityLevel;
    private double dataQualityTrend;
    private String dataCorrectionLink;
    private String label;

    public void processQualityLevel(List<KpiDTO> lineKPITrendingList)
    {
        if(this.totalOverallInspected!=0)
        {
            this.discrepancyLevel=getRoundDouble(((double)this.totalDiscrepancy/(double)this.totalOverallInspected)*100);
            this.dataQualityLevel= getRoundDouble(100-this.discrepancyLevel);

           lineKPITrendingList.stream().filter(x->x.getTypeKPI().equals(this.typeKPI)).findFirst().ifPresent(
                   lineKPI ->  {
                        double discrepancyLevelTrending =((double)lineKPI.totalDiscrepancy/(double)lineKPI.totalOverallInspected)*100;
                        double dataQualityLevelTrending =100-discrepancyLevelTrending;
                        this.dataQualityTrend =  getRoundDouble(this.dataQualityLevel-dataQualityLevelTrending);
                    }
            );

        }

        switch (this.typeKPI) {
            case MISSING_SITE -> {
                this.label = "Devices associated to inactive sites";
                this.dataCorrectionLink = "";
            }
            case INACTIVE_SITE -> {
                this.label = "Devices with missing site information";
                this.dataCorrectionLink = "";
            }
            default -> {
                this.label = "unknown kpi";
                this.dataCorrectionLink = "";
            }
        }
    }



    private Double getRoundDouble(Double number)
    {
        return  Math.round(number*100.0)/100.0;

    }




}
