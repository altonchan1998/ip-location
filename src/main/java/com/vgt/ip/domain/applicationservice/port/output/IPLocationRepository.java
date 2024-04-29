package com.vgt.ip.domain.applicationservice.port.output;

import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import reactor.core.publisher.Mono;


public interface IPLocationRepository {
    void clearLocalCache();

    Mono<Void> removeByIPAndVersionLessThan(String ip, Long version);

    Mono<IPLocationDTO> findByIP(String ip);
}
