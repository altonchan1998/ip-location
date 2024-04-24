package com.vgt.ip.dataaccess.iplocation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IPLocationDetailEntity implements Serializable {
    private String country;
    private String province;
    private String district;
    private String city;
    private String lang;
    private String isp;
    private String description;
    private String isChinaRegion;
    private String region;
    @JsonProperty("iso_code")
    private String isoCode;
    @JsonProperty("time_zone")
    private String timeZone;
    private Integer lib;
}