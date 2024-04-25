package com.vgt.ip.domain.applicationservice.dto.iplocation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IPLocationDTO {
    private String primaryInfo;
    private String country;
    private String province;
    private String district;
    private String city;
    private String lang;
    private String isp;
    private String description;
    private Boolean isChinaRegion;
    private String region;
    private Boolean vd001ThirdPayEnabled;
    private String isoCode;
    private String timeZone;
    private String ip;
    private Integer lib;
}
