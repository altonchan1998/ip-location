package com.vgt.ip.dataaccess.iplocationversion;

import com.vgt.ip.dataaccess.iplocationversion.repository.IPLocationVersionRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionServiceImpl implements IPLocationVersionService {
    private final IPLocationVersionRedisRepository ipLocationVersionRedisRepository;

    private volatile Long ipLocationVersion = -1L;

    @Override
    public void refreshLocalIPLocationVersion() {
        ipLocationVersionRedisRepository.getRedisIPLocationVersion()
                .doOnNext(this::updateIPLocationVersion)
                .subscribe();
    }

    private void updateIPLocationVersion(Long versionFromRedis) {
        if (canUpdateIPLocationVersion.test(versionFromRedis)) {
            ipLocationVersion = versionFromRedis;
            log.info("IPLocationVersion updated to {} from {}", versionFromRedis, ipLocationVersion);
        } else {
            log.info("IPLocationVersion is up to date - redis: {}, local: {}", versionFromRedis, ipLocationVersion);
        }
    }

    private final Predicate<Long> canUpdateIPLocationVersion = versionFromRedis -> Objects.isNull(ipLocationVersion) || versionFromRedis > ipLocationVersion;

    @Override
    public Long getLocalIPLocationVersion() {
        return ipLocationVersion;
    }
}
