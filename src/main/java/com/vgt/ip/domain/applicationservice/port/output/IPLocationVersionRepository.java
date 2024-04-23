package com.vgt.ip.domain.applicationservice.port.output;

import reactor.core.publisher.Mono;

public interface IPLocationVersionRepository {
    void saveIPLocationLocalVersion(Long version);

    Long findIpLocationLocalVersion();

    Mono<Long> findRemoteIPLocationVersion();
}
