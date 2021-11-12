package com.obs.dqsc.api.domain.document;

import com.obs.dqsc.api.util.enums.Type;
import com.obs.dqsc.api.domain.document.embedded.Customer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author KDFL4681
 * @since 11-09-2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "kpi")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Kpi {
    @Id
    private ObjectId id;
    private Type type;
    private Customer customer;
    private int totalInspected;
    private int discrepancy;
    private LocalDateTime receptionDate;
    private LocalDate extractionDate;
}
