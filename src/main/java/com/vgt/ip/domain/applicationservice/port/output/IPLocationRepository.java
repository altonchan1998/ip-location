package com.vgt.ip.domain.applicationservice.port.output;

import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import reactor.core.publisher.Mono;


public interface IPLocationRepository {
    void clearLocalCache();

    Mono<IPLocationDTO> findByIPAndVersionNotLessThan(String ip, long version);
}
