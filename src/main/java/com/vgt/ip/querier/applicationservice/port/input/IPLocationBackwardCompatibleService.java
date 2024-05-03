package com.vgt.ip.querier.applicationservice.port.input;

import com.vgt.ip.querier.applicationservice.dto.Result;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationQuery;
import reactor.core.publisher.Mono;


public interface IPLocationBackwardCompatibleService {
    Mono<Result<Object>> getIp(IPLocationQuery query);
}
