package com.vgt.ip.domain.applicationservice.handler;


import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.BiPredicate;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationVersionRefreshCommandHandler implements Handler<Void, Void> {

    private final IPLocationVersionRepository ipLocationVersionRepository;

    private void updateIPLocationVersion(Long remoteVersion) {
        Long localVersion = ipLocationVersionRepository.findIpLocationLocalVersion();
        if (Objects.isNull(remoteVersion)) {
            log.warn("IPLocationVersion not found in redis, will not update local ipLocationVersion, current local IPLocationVersion is {}", localVersion);
        } else if (canUpdateIPLocationVersion.test(remoteVersion, localVersion)) {
            ipLocationVersionRepository.saveIPLocationLocalVersion(remoteVersion);
            log.info("IPLocationVersion updated to {}", remoteVersion);
        } else {
            log.info("IPLocationVersion is up to date - redis: {}, local: {}", remoteVersion, localVersion);
        }
    }

    private final BiPredicate<Long, Long> canUpdateIPLocationVersion = (remoteVersion, localVersion) -> Objects.isNull(localVersion) || remoteVersion > localVersion;

    @Override
    public Mono<Void> handle(Void unused) {
        return ipLocationVersionRepository.findRemoteIPLocationVersion()
                .doOnSuccess(this::updateIPLocationVersion)
                .then();
    }
}
