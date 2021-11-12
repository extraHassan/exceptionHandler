package com.obs.dqsc.api.domain.document.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstalledResource {
    private String installedResourceId;
    private String irType;
    private String routerName;
    private String provisionedStatus;
    private String provisioningStatus;
    private String finvPollingStatus;
    private String detectedChassisType;
    private String detectedMainIpAddress;
    private String osVersion;
    private String serialNumber;
    private String localBandWidthPoll;
}
