package com.obs.dqsc.api.service;


import com.obs.dqsc.api.domain.dto.KpiDTO;
import com.obs.dqsc.api.repository.KpiRepository;
import com.obs.dqsc.api.repository.mongo_template.KpiDTORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class KpiServiceImpl implements KpiService {
    private final KpiRepository kpiDao;
    private final KpiDTORepository kpiDTORepository;

    public List<KpiDTO> findKPILineByCustomer(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, List<String> enterpriseId, LocalDate extractionDate) {
        return kpiDTORepository.findKPILineByCustomer(entityPerimeter, name, ic01, siren, enterpriseId, extractionDate);
    }
}
