package com.obs.dqsc.api.service;

import com.obs.dqsc.api.domain.document.ReportInactiveSite;
import com.obs.dqsc.api.domain.document.ReportMissingSite;
import com.obs.dqsc.api.repository.InactiveSiteReportRepository;
import com.obs.dqsc.api.repository.MissingSiteReportRepository;
import com.obs.dqsc.api.util.enums.Type;
import com.obs.dqsc.api.domain.document.Report;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final MissingSiteReportRepository missingSiteReportDao;
    private final InactiveSiteReportRepository inactiveSiteReportDao;

    @Override
    public List<Report> findReportByCustomerAndDate(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, LocalDate extractionDate, Type reportType) {
        Optional<List<Report>> reports = Optional.of(new ArrayList<>());
        boolean allCustomerAttributesEmpty = entityPerimeter.isEmpty() && name.isEmpty() && ic01.isEmpty() && siren.isEmpty() ;
        if (reportType.equals(Type.MISSING_SITE)) {
            final Optional<List<ReportMissingSite>> byCustomerAndDate = allCustomerAttributesEmpty ? missingSiteReportDao.findByExtractionDate(extractionDate)
            :missingSiteReportDao.findByCustomerAndDate(entityPerimeter, name, ic01, siren, extractionDate);
            byCustomerAndDate.ifPresent(reports.get()::addAll);
        } else if (reportType.equals(Type.INACTIVE_SITE)) {
            final Optional<List<ReportInactiveSite>> byCustomerAndDate =  allCustomerAttributesEmpty ? inactiveSiteReportDao.findByExtractionDate(extractionDate)
                    : inactiveSiteReportDao.findByCustomerAndDate(entityPerimeter, name, ic01, siren, extractionDate);
            byCustomerAndDate.ifPresent(reports.get()::addAll);
        }
        return reports.filter(kpis -> !kpis.isEmpty()).orElse(null);
    }

}
