package com.vgt.ip.domain.applicationservice.port.input;

import com.vgt.ip.domain.applicationservice.dto.Result;

import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import reactor.core.publisher.Mono;



public interface IPLocationBackwardCompatibleService {
    Mono<Result<Object>> getIp(IPLocationQuery query);
}
