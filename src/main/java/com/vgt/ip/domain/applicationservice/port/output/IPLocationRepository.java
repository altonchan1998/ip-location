package com.vgt.ip.domain.applicationservice.port.output;

import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.core.valueobject.IPAddress;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IPLocationRepository {
    Mono<Void> clearLocalCache();

    Mono<IPLocationDTO> findByIPAddress(IPAddress ipAddress);

    Mono<List<Void>> buildLocalCache(List<String> ipList);
}
