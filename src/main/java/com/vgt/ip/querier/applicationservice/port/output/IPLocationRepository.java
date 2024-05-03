package com.vgt.ip.querier.applicationservice.port.output;

import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationDTO;
import reactor.core.publisher.Mono;


public interface IPLocationRepository {
    void clearLocalCache();

    Mono<IPLocationDTO> findByIPAndVersionNotLessThan(String ip, long version);
}
