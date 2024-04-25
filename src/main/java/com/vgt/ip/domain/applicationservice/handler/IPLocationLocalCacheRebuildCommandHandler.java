package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationLocalCacheRebuildCommandHandler implements Handler<List<String>, Void> {

    private final IPLocationRepository ipLocationRepository;

    @Override
    public Mono<Void> handle(List<String> ipList) {
        return ipLocationRepository.clearLocalCache()
                .thenMany(Flux.fromIterable(ipList))
                .then(ipLocationRepository.buildLocalCache(ipList))
                .doOnSuccess(unused -> log.info("Rebuild IPLocation Local Cache Success"))
                .then();
    }
}
