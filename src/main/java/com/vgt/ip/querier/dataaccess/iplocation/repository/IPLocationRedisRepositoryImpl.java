package com.vgt.ip.querier.dataaccess.iplocation.repository;

import com.vgt.ip.querier.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.querier.exception.IPApplicationDataAccessException;
import com.vgt.ip.querier.mapper.RedisKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static com.vgt.ip.querier.constant.RedisConstants.REDIS_TTL_DURATION_DEFAULT;


@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRedisRepositoryImpl {
    private final ReactiveRedisOperations<String, IPLocationMongoEntity> ipLocationOps;
    private final RedisKeyMapper redisKeyMapper;

    public Mono<Boolean> deleteByIP(String ip) {
        return ipLocationOps
                .opsForValue()
                .delete(redisKeyMapper.toIPLocationKey(ip))
                .doOnSubscribe(s -> log.debug("Deleting IPLocation of {} in Redis", ip))
                .doOnSuccess(s -> log.debug("Deleted IPLocation of {} in Redis", ip))
                .onErrorMap(e -> new IPApplicationDataAccessException("Delete IPLocation of " + ip + " in Redis failed"));
    }

    public Mono<IPLocationMongoEntity> findByIP(String ip) {
        return ipLocationOps
                .opsForValue()
                .get(redisKeyMapper.toIPLocationKey(ip))
                .doOnSubscribe(s -> log.debug("Finding IPLocation of {} in Redis", ip))
                .onErrorMap(it -> new IPApplicationDataAccessException("Find IPLocation of " + ip + " in Redis failed"));
    }

    public Mono<Boolean> save(IPLocationMongoEntity entity) {
        return ipLocationOps
                .opsForValue()
                .set(redisKeyMapper.toIPLocationKey(entity.getIp()), entity, REDIS_TTL_DURATION_DEFAULT)
                .doOnSubscribe(s -> log.debug("Saving IPLocation to Redis, ip: {}", entity.getIp()))
                .doOnSuccess(it -> log.debug("Saved IPLocation to Redis, ip: {}", entity.getIp()))
                .onErrorMap(e -> new IPApplicationDataAccessException("Save IPLocation of " + entity.getIp() + " to Redis failed"));
    }
}