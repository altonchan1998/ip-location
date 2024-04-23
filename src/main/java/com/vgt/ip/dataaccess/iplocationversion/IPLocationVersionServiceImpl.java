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
                .doOnError(e -> log.error("Failed to update IPLocationVersion, current local IPLocationVersion is {}", ipLocationVersion, e))
                .subscribe();
    }

    private void updateIPLocationVersion(Long versionFromRedis) {
        if (Objects.isNull(versionFromRedis)) {
            log.warn("IPLocationVersion not found in redis, will not update local ipLocationVersion, current local IPLocationVersion is {}", ipLocationVersion);
        } else if (canUpdateIPLocationVersion.test(versionFromRedis)) {
            ipLocationVersion = versionFromRedis;
            log.info("IPLocationVersion updated to {}", versionFromRedis);
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
