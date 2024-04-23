package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.mapper.IPLocationApplicationServiceDataMapper;
import com.vgt.ip.domain.core.valueobject.IPAddress;
import com.vgt.ip.exception.IPLocationNotFoundException;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationQueryHandler implements Handler<IPLocationQuery, Mono<Result<IPLocationResponse>>> {

    private final IPLocationApplicationServiceDataMapper ipLocationApplicationServiceDataMapper;
    private final IPLocationRepository ipLocationRepository;

    public Mono<Result<IPLocationResponse>> handle(IPLocationQuery query) {
        IPAddress ipAddress = new IPAddress(query.getIp());

        return ipLocationRepository.findByIPAddress(ipAddress)
                .map(ipLocationApplicationServiceDataMapper::toResult)
                .switchIfEmpty(Mono.error(new IPLocationNotFoundException("IPLocation not found. IP: " + query.getIp())));
    }
}
