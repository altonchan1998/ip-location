package com.vgt.ip.repository.redis;

import com.vgt.ip.model.entity.IPLocation;
import com.vgt.ip.utils.RedisKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRedisRepository {
    private final ReactiveRedisOperations<String, IPLocation> ipLocationOps;
    private final RedisKeyMapper redisKeyMapper;

    public Mono<IPLocation> getIPLocationByIP(String ip) {
        String key = redisKeyMapper.toIPLocationKey(ip);
        log.info("Fetching IPLocation from redis - key: {}", key);
        return ipLocationOps.opsForValue().get(key);
    }

    public Mono<List<IPLocation>> getIPLocationByIPBatch(List<String> ipList) {
        List<String> keys = redisKeyMapper.toIPLocationKeys(ipList);
        log.info("Fetching IPLocation from redis - key: {}", keys);
        return ipLocationOps.opsForValue().multiGet(keys);
    }

    public void saveIPLocation(IPLocation ipLocation) {
        String key = redisKeyMapper.toIPLocationKey(ipLocation.getIp());
        ipLocationOps.opsForValue().set(key, ipLocation).subscribe();
    }
}
