package com.vgt.ip.domain.applicationservice.port.input;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPLocationApplicationService {
    Mono<Result<?>> getIPLocationByIP(IPLocationQuery ipLocationQuery);

    Mono<Void> refreshLocalIPLocationVersion();

    void rebuildIPLocationLocalCache(List<String> ipList);
}
