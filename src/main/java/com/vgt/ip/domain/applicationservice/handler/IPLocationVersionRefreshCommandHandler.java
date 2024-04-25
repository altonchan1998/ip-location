package com.vgt.ip.domain.applicationservice.handler;


import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import com.vgt.ip.util.LockRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;


/**
 * The IPLocationVersionRefreshCommandHandler class is responsible for handling the IP location local version refresh command.
 * It refreshes the local version by fetching the latest IP Location version from the Redis and updating the version stored in the local cache accordingly.
 *  - The local version is updated only if the remote version is greater than the local version.
 *
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationVersionRefreshCommandHandler implements Handler<Void, Void> {

    private final IPLocationVersionRepository ipLocationVersionRepository;
    private final IPLocationRepository ipLocationRepository;

    private void updateIPLocationVersion(Long remoteVersion) {
        Long localVersion = ipLocationVersionRepository.findLocalIpLocationVersion();

        if (canUpdateIPLocationLocalVersion.test(remoteVersion, localVersion)) {
            ipLocationVersionRepository.saveLocalIPLocationVersion(remoteVersion);
            log.info("IPLocationVersion updated to {}", remoteVersion);
        } else {
            log.info("IPLocationVersion is up to date - redis: {}, local: {}", remoteVersion, localVersion);
        }
    }

    private final BiPredicate<Long, Long> canUpdateIPLocationLocalVersion = (remoteVersion, localVersion) -> Objects.isNull(localVersion) || remoteVersion > localVersion;

    @Override
    public Mono<Void> handle(Void unused) {
        return ipLocationVersionRepository.findRemoteIPLocationVersion()
                .filter(remoteVersion -> remoteVersion > ipLocationVersionRepository.findLocalIpLocationVersion())
                .doOnNext(remoteVersion -> rebuildLocalCacheWithLock().subscribe())
                .doOnNext(this::updateIPLocationVersion) // TODO: Refactor Code
                .then();
    }

    private Mono<Void> rebuildLocalCacheWithLock() {
        return Mono.defer(() -> {
            if (LockRegistry.getIpLocationCacheLock().compareAndSet(false, true)) {
                return ipLocationRepository.clearLocalCache()
                        .doOnSubscribe(it -> log.info("Acquired lock by buildIPLocationLocalCache"))
                        .then(ipLocationRepository.buildLocalCache(List.of()))
                        .doFinally(it -> {
                            log.info("Released lock from buildIPLocationLocalCache");
                            LockRegistry.getIpLocationCacheLock().set(false);
                        });
            }
            return Mono.empty();
        });
    }
}
