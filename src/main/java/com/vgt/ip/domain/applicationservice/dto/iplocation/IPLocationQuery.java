package com.vgt.ip.domain.applicationservice.dto.iplocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPLocationQuery {
    private String ip;
}
