package com.vgt.ip.dataaccess.iplocation.repository;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationRedisEntity;
import com.vgt.ip.mapper.RedisKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRedisRepository {
    private final ReactiveRedisOperations<String, IPLocationRedisEntity> ipLocationOps;
    private final RedisKeyMapper redisKeyMapper;

    public Mono<IPLocationRedisEntity> findByIP(String ip) {
        return ipLocationOps
                .opsForValue()
                .get(redisKeyMapper.toIPLocationKey(ip));
    }

    public void save(IPLocationRedisEntity entity) {
        ipLocationOps
                .opsForValue()
                .set(redisKeyMapper.toIPLocationKey(entity.getIp()), entity)
                .doOnSuccess(it -> log.info("Save IPLocation to Redis success, ip: {}", entity.getIp()))
                .doOnError(e -> log.error("save IPLocation to Redis failed, ip: {}", entity.getIp(), e))
                .subscribe();
    }
}
