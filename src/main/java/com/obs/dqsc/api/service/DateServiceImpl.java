package com.obs.dqsc.api.service;

import com.obs.dqsc.api.repository.mongo_template.DateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DateServiceImpl implements DateService {
    private final DateRepository dateRepository;

    public List<LocalDate> findDistinctExtractionDates() {
        return dateRepository.findDistinctExtractionDates();
    }
}
