package com.vgt.ip.repository.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;


import static com.vgt.ip.constant.RedisConstants.REDIS_KEY_IP_LOCATION_VERSION;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionRedisRepository {
    private final ReactiveRedisOperations<String, Long> ipLocationVersionOps;

    public Long getRedisIPLocationVersion() {
        String key = REDIS_KEY_IP_LOCATION_VERSION;
        log.info("Fetching IPLocationVersion from redis - key: {}", key);
        return ipLocationVersionOps.opsForValue().get(key).block();
    }
}
