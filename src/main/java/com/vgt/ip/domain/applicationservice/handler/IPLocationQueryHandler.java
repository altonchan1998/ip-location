package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.helper.IPHelper;
import com.vgt.ip.domain.applicationservice.mapper.IPLocationApplicationServiceDataMapper;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import com.vgt.ip.exception.IPLocationNotFoundException;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.BiPredicate;

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
