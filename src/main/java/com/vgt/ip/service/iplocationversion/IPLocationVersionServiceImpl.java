package com.vgt.ip.service.iplocationversion;

import com.vgt.ip.repository.redis.IPLocationVersionRedisRepository;
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
    public void updateLocalIPLocationVersion() {
        Long versionFromRedis = ipLocationVersionRedisRepository.getRedisIPLocationVersion();

        if (Objects.isNull(versionFromRedis)) {
            log.warn("IPLocationVersion not found in redis, will not update local ipLocationVersion");
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
