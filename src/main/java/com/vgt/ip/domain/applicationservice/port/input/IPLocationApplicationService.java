package com.vgt.ip.domain.applicationservice.port.input;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPLocationApplicationService {
    Mono<Result<IPLocationResponse>> getIPLocationByIP(IPLocationQuery ipLocationQuery);
    Mono<Void> refreshLocalIPLocationVersion();

    Mono<Void> buildIPLocationLocalCache(List<String> ipList);

//    Mono<Void> rebuildIPLocationLocalCache(List<String> ipList);
}
