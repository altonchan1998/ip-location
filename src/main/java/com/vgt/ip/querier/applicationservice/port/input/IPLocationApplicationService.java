package com.vgt.ip.querier.applicationservice.port.input;

import com.vgt.ip.querier.applicationservice.dto.Result;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationResponse;
import reactor.core.publisher.Mono;


public interface IPLocationApplicationService {
    Mono<Result<IPLocationResponse>> getIPLocationByIP(IPLocationQuery ipLocationQuery);

    Mono<Void> refreshLocalIPLocationVersion();
}
