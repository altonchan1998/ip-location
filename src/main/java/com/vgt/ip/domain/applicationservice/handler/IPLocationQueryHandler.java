package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.mapper.IPLocationApplicationServiceDataMapper;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.domain.core.valueobject.IPAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static com.vgt.ip.utils.IpAddressResolver.getClientIp;
import static com.vgt.ip.utils.IpAddressResolver.isValidIp;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationQueryHandler implements Handler<IPLocationQuery, Result<?>> {

    private final IPLocationApplicationServiceDataMapper ipLocationApplicationServiceDataMapper;
    private final IPLocationRepository ipLocationRepository;

    public Mono<Result<?>> handle(IPLocationQuery query) {
        log.info("getIPLocationByIP: {}", query.getIps());
        if (query.getIps() == null) {
            return Mono.just(getClientIp(query.getHeaders()))
                    .flatMap(clientIp -> this.getIpInfo(clientIp, query.getHeaders().get("uuid")))
                    .map(ipLocationApplicationServiceDataMapper::toResult);
        }

        return Flux.fromIterable(query.getIps())
                .flatMap(clientIp -> this.getIpInfo(clientIp, query.getHeaders().get("uuid")))
                .collect(Collectors.toList())
                .map(ipLocationApplicationServiceDataMapper::toResult);
    }

    private Mono<IPLocationDTO> getIpInfo(String clientIp, String uuid) {
        IPAddress ipAddress = IPAddress.builder().ip(clientIp).build();
        return Mono.just(isValidIp(clientIp))
                .flatMap(res -> res ? ipLocationRepository.findByIPAddress(ipAddress) :
                        Mono.error(new IllegalArgumentException("clientIp is not valid")));

    }

}
