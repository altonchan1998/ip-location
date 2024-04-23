package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.mapper.IPLocationDataMapper;
import com.vgt.ip.domain.core.exception.IPLocationNotFoundException;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationQueryHandler {

    private final IPLocationDataMapper ipLocationDataMapper;
    private final IPLocationRepository ipLocationRepository;

    public Mono<Result<IPLocationResponse>> handle(IPLocationQuery query) {
        String ip = query.getIp();
        return ipLocationRepository.getIPLocationByIP(ip)
                .map(ipLocationDataMapper::toResult)
                .switchIfEmpty(Mono.error(new IPLocationNotFoundException(ip)));
    }
}
