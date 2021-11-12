package com.obs.dqsc.api.repository.mongo_template;

import java.time.LocalDate;
import java.util.List;

public interface DateRepository {
    List<LocalDate> findDistinctExtractionDates();

}
