package com.vgt.ip.dataaccess.iplocation.repository;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.vgt.ip.dataaccess.iplocation.entity.IPLocationCaffeineEntity;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class IPLocationCaffeineRepository {

    AsyncCache<String, IPLocationCaffeineEntity> cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .buildAsync();

    public Mono<IPLocationCaffeineEntity> findIPLocationByIP(String ip) {
        return Mono.fromCompletionStage(() -> cache.getIfPresent(ip));
    }

    public void save(IPLocationCaffeineEntity entity) {
       cache.put(entity.getIp(), CompletableFuture.completedFuture(entity));
    }

    public void cleanAll() {
        cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .buildAsync();
    }
}
