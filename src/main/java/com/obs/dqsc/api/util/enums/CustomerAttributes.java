package com.obs.dqsc.api.util.enums;

public enum CustomerAttributes {
    ENTITY_PERIMETER("entityPerimeter"),
    NAME("name"),
    SIREN("siren"),
    ENTERPRISE_ID("enterpriseId"),
    IC_01("ic01");

    private final String value;

    public String value() {
        return value;
    }

    CustomerAttributes(String value) {
        this.value = value;
    }


}
