package com.vgt.ip.domain.applicationservice.port.output;

import reactor.core.publisher.Mono;

public interface IPLocationVersionRepository {
    void saveLocalIPLocationVersion(long version);

    Long findLocalIpLocationVersion();

    Mono<Long> findRemoteIPLocationVersion();
}
