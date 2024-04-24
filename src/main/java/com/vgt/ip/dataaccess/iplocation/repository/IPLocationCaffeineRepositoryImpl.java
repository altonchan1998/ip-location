package com.vgt.ip.dataaccess.iplocation.repository;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Slf4j
@Repository
public class IPLocationCaffeineRepositoryImpl {

    AsyncCache<String, IPLocationMongoEntity> cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .buildAsync();

    public Mono<IPLocationMongoEntity> findIPLocationByIP(String ip) {
        return Mono.fromFuture(() -> cache.get(ip, k -> null))
                .doOnSubscribe(s -> log.debug("Find IPLocation of {} in Caffeine Cache", ip))
                .doOnError(e -> log.error("Find IPLocation of {} in Caffeine Cache failed", ip, e));
    }

    public void save(IPLocationMongoEntity entity) {
        log.debug("Saving IPLocation of {} into cache", entity.getIp());
        cache.put(entity.getIp(), CompletableFuture.completedFuture(entity));
    }

    public void cleanAll() {
        cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .buildAsync();
    }
}
