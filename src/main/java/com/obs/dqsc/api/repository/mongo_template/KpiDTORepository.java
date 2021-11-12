package com.obs.dqsc.api.repository.mongo_template;

import com.obs.dqsc.api.domain.dto.KpiDTO;

import java.time.LocalDate;
import java.util.List;

public interface KpiDTORepository {
    List<KpiDTO> findKPILineByCustomer(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate);
}
