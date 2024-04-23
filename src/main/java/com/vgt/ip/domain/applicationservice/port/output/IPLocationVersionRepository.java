package com.vgt.ip.domain.applicationservice.port.output;

import reactor.core.publisher.Mono;

public interface IPLocationVersionRepository {
    Mono<Long> getIPLocationVersion();
}
