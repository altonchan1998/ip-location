package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.util.LockRegistry;
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
        return Mono.defer(() -> {
            if (LockRegistry.getIpLocationCacheLock().compareAndSet(false, true)) {
                log.info("Acquired lock by buildIPLocationLocalCache");
                return ipLocationRepository.buildLocalCache(ipList)
                        .doOnSubscribe(unused -> log.info("Build IPLocation Local Cache started"))
                        .doOnSuccess(unused -> log.info("Build IPLocation Local Cache finished"))
                        .doOnError(error -> log.error("Build IPLocation Local Cache failed", error))
                        .doFinally(unused -> {
                            log.info("Released lock from buildIPLocationLocalCache");
                            LockRegistry.getIpLocationCacheLock().set(false);
                        });
            } else {
                log.warn("Command skipped as failed to acquire lock for buildIPLocationLocalCache");
                return Mono.empty();
            }}
        );

    }
}
