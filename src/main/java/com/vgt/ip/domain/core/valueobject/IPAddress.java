package com.vgt.ip.domain.core.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IPAddress {
    private final String ip;
}
