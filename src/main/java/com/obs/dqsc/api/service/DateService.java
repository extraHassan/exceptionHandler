package com.obs.dqsc.api.service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author KDFL4681
 * @since 11-09-2021
 */
public interface DateService {
    List<LocalDate> findDistinctExtractionDates();
}
