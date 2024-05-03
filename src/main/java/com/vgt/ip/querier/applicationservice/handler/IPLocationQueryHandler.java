package com.vgt.ip.querier.applicationservice.handler;

import com.vgt.ip.querier.applicationservice.dto.Result;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.querier.applicationservice.helper.IPHelper;
import com.vgt.ip.querier.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.querier.applicationservice.port.output.IPLocationVersionRepository;
import com.vgt.ip.querier.mapper.IPLocationApplicationServiceDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationQueryHandler implements Handler<IPLocationQuery, Result<IPLocationResponse>> {

    private final IPLocationApplicationServiceDataMapper ipLocationApplicationServiceDataMapper;
    private final IPLocationRepository ipLocationRepository;
    private final IPLocationVersionRepository ipLocationVersionRepository;

    private final IPHelper ipHelper;

    public Mono<Result<IPLocationResponse>> handle(IPLocationQuery query) {

        return Mono.empty();
    }

}
