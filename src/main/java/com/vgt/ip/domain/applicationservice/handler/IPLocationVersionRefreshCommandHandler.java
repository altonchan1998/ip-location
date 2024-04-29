package com.vgt.ip.domain.applicationservice.handler;


import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.BiPredicate;


/**
 * The IPLocationVersionRefreshCommandHandler class is responsible for handling the IP location local version refresh command.
 * It refreshes the local version by fetching the latest IP Location version from the Redis and updating the version stored in the local cache accordingly.
 *  - The local version is updated only if the remote version is greater than the local version.
 *  - The local cache is cleared once the local version is updated.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationVersionRefreshCommandHandler implements Handler<Void, Void> {

    private final IPLocationVersionRepository ipLocationVersionRepository;
    private final IPLocationRepository ipLocationRepository;

    private final BiPredicate<Long, Long> canUpdateIPLocationLocalVersion = (remoteVersion, localVersion) -> Objects.isNull(localVersion) || remoteVersion > localVersion;

    @Override
    public Mono<Void> handle(Void unused) {
        return ipLocationVersionRepository.findRemoteIPLocationVersion()
                .doOnSuccess(remoteVersion -> log.info("Remote IPLocationVersion: {}, Local IPLocationVersion: {}", remoteVersion, ipLocationVersionRepository.findLocalIpLocationVersion()))
                .filter(remoteVersion -> canUpdateIPLocationLocalVersion.test(remoteVersion, ipLocationVersionRepository.findLocalIpLocationVersion()))
                .doOnNext(
                        remoteVersion -> Mono.fromRunnable(() -> ipLocationVersionRepository.saveLocalIPLocationVersion(remoteVersion))
                                .doOnSuccess(unused1 -> log.info("Local IPLocationVersion updated: {}", remoteVersion))
                                .subscribe()
                )
                .doOnNext(remoteVersion -> ipLocationRepository.clearLocalCache())
                .then();
    }
}
