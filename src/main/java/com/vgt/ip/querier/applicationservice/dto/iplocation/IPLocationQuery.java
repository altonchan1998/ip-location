package com.vgt.ip.querier.applicationservice.dto.iplocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPLocationQuery {
    private Map<String, String> headers;
    private List<String> ipList;
}