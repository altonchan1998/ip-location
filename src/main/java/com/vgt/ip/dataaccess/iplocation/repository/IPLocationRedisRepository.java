package com.vgt.ip.dataaccess.iplocation.repository;

import com.vgt.ip.dataaccess.iplocation.entity.redis.IPLocationRedisEntity;
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

    public Mono<IPLocationRedisEntity> findIPLocationByIP(String ip) {
        return ipLocationOps
                .opsForValue()
                .get(redisKeyMapper.toIPLocationKey(ip));
    }
}
