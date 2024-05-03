package com.vgt.ip.querier.dataaccess.iplocationversion.repository;

import com.vgt.ip.querier.exception.IPApplicationDataAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static com.vgt.ip.querier.constant.RedisConstants.REDIS_KEY_IP_LOCATION_VERSION;


@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionRedisRepository {
    private final ReactiveRedisOperations<String, Long> ipLocationVersionOps;

    public Mono<Long> getRedisIPLocationVersion() {
        String key = REDIS_KEY_IP_LOCATION_VERSION;
        return ipLocationVersionOps.opsForValue().get(key)
                .doOnSubscribe(s -> log.debug("Fetching IPLocationVersion from redis - key: {}", key))
                .doOnSuccess(version -> log.debug("Fetch IPLocationVersion from redis success - version: {}", version))
                .onErrorMap(e -> new IPApplicationDataAccessException("Fetch IPLocationVersion from redis failed"));
    }
}
