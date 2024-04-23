package com.vgt.ip.domain.applicationservice.port.output;

import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.core.valueobject.IPAddress;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IPLocationRepository {
    void rebuildLocalCache(List<String> ip);

    Mono<IPLocationDTO> findByIPAddress(IPAddress ipAddress);
}
