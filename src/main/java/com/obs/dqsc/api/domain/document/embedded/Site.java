package com.obs.dqsc.api.domain.document.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Site {
    private String id;
    private String name;
    private String costumerSiteName;
    private String status;
    private String streetName;
    private String postalCode;
}
