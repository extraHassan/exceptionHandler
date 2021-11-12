package com.obs.dqsc.api.domain.document.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author KDFL4681
 * @since 11-09-2021
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Customer {
    private String entityPerimeter;
    private String name;
    private String siren;
    private String enterpriseId;
    private String ic01;


}
