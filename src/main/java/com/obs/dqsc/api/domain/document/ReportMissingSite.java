package com.obs.dqsc.api.domain.document;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author
 * @since 11-09-2021
 */
@Getter
@Setter
@ToString
@Document(collection = "missing_site_report")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReportMissingSite extends Report {
    @Id
    private ObjectId id;

}
