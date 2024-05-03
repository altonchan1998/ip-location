package com.vgt.ip.querier.applicationservice.dto.iplocation;


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
    private long version;
}
