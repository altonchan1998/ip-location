package com.vgt.ip.dataaccess.iplocation.repository;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.mapper.RedisKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRedisRepositoryImpl {
    private final ReactiveRedisOperations<String, IPLocationMongoEntity> ipLocationOps;
    private final RedisKeyMapper redisKeyMapper;

    public Mono<IPLocationMongoEntity> findByIP(String ip) {
        return ipLocationOps
                .opsForValue()
                .get(redisKeyMapper.toIPLocationKey(ip))
                .doOnSubscribe(s -> log.debug("Finding IPLocation of {} in Redis", ip))
                .doOnError(e -> log.error("Find IPLocation of {} in Redis failed", ip, e));
    }

    public Mono<Boolean> save(IPLocationMongoEntity entity) {
        return ipLocationOps
                .opsForValue()
                .set(redisKeyMapper.toIPLocationKey(entity.getIp()), entity)
                .doOnSuccess(it -> log.debug("Save IPLocation to Redis success, ip: {}", entity.getIp()))
                .doOnError(e -> log.error("save IPLocation to Redis failed, ip: {}", entity.getIp(), e));
    }
}
