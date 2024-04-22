package com.vgt.ip.repository.redis;

import com.vgt.ip.mapper.RedisKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.function.Predicate;

import static com.vgt.ip.constant.RedisConstants.REDIS_KEY_IP_LOCATION_VERSION;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionRedisRepository {
    private final ReactiveRedisOperations<String, Long> ipLocationVersionOps;
    private final RedisKeyMapper redisKeyMapper;

    private volatile Long ipLocationVersion = -1L;

    public void updateIPLocationVersion() {
        String key = REDIS_KEY_IP_LOCATION_VERSION;
        log.info("Fetching IPLocationVersion from redis - key: {}", key);
        Long versionFromRedis = ipLocationVersionOps.opsForValue().get(key).block();
        if (Objects.isNull(versionFromRedis)) {
            log.warn("IPLocationVersion not found in redis - key: {}, will not update local ipLocationVersion", key);
        } else if (canUpdateIPLocationVersion.test(versionFromRedis)) {
            ipLocationVersion = versionFromRedis;
            log.info("IPLocationVersion updated to {}", versionFromRedis);
        } else {
            log.info("IPLocationVersion is up to date - redis: {}, local: {}", versionFromRedis, ipLocationVersion);
        }
    }

    private final Predicate<Long> canUpdateIPLocationVersion = versionFromRedis ->  Objects.isNull(ipLocationVersion) || versionFromRedis > ipLocationVersion;

    public Long getIPLocationVersion() {
        return ipLocationVersion;
    }
}
