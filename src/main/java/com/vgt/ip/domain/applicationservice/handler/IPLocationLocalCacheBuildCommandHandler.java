package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationLocalCacheBuildCommandHandler implements Handler<List<String>, Void> {

    private final IPLocationRepository ipLocationRepository;

    @Override
    public Mono<Void> handle(List<String> ipList) {
        return ipLocationRepository.clearLocalCache()
                .then(ipLocationRepository.buildLocalCache(ipList))
                .doOnSuccess(unused -> log.info("Rebuilt IPLocation Local Cache"))
                .map(unused -> null);
    }
}
