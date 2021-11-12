package com.obs.dqsc.api.domain.document;

import com.obs.dqsc.api.util.enums.Type;
import com.obs.dqsc.api.domain.document.embedded.Customer;
import com.obs.dqsc.api.domain.document.embedded.InstalledResource;
import com.obs.dqsc.api.domain.document.embedded.Site;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author
 * @since 11-09-2021
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Report {
    private Type type;
    private Customer customer;
    private InstalledResource installedResource;
    private Site site;
    private String offerSpecificationOfferLabel;
    private String installedOfferLastOrderRefio;
    private LocalDate extractionDate;
    private LocalDateTime receptionDate;
}
