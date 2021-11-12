package com.obs.dqsc.api.service;

import com.obs.dqsc.api.util.enums.Type;
import com.obs.dqsc.api.domain.document.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<Report> findReportByCustomerAndDate(List<String> entityPerimeter, List<String> name, List<String> ic01, List<String> siren, LocalDate extractionDate, Type reportType);
}
