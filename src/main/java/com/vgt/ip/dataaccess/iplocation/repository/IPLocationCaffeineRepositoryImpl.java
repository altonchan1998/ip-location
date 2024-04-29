package com.vgt.ip.dataaccess.iplocation.repository;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.exception.IPApplicationDataAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class IPLocationCaffeineRepositoryImpl {

    AsyncCache<String, IPLocationMongoEntity> cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .buildAsync();

    public Mono<IPLocationMongoEntity> findIPLocationByIP(String ip) {
        return Mono.fromFuture(() -> cache.get(ip, k -> null))
                .doOnSubscribe(s -> log.debug("Finding IPLocation of {} in Caffeine Cache", ip))
                .onErrorMap(it -> new IPApplicationDataAccessException("Find IPLocation of " + ip + " in Caffeine Cache failed"));
    }

    public Mono<Void> save(IPLocationMongoEntity entity) {
        return Mono.fromRunnable(() -> cache.put(entity.getIp(), CompletableFuture.completedFuture(entity)))
                .then()
                .doOnSuccess(s -> log.debug("Saved IPLocation of {} to Caffeine Cache", entity.getIp()))
                .onErrorMap(e -> new IPApplicationDataAccessException("Save IPLocation of " + entity.getIp() + " to Caffeine Cache failed"));
    }

    public Mono<Void> deleteByKey(String key) {
        return Mono.fromRunnable(() -> cache.asMap().remove(key))
                .doOnSubscribe(s -> log.debug("Removing IPLocation of {} from Caffeine Cache", key))
                .doOnSuccess(s -> log.debug("Removed IPLocation of {} from Caffeine Cache", key))
                .then()
                .onErrorMap(e -> new IPApplicationDataAccessException("Remove IPLocation of " + key + " from Caffeine Cache failed"));
    }

    public Mono<Void> cleanAll() {
        return Mono.fromRunnable(() -> cache.asMap().clear())
                .doOnSubscribe(s -> log.debug("Clearing IPLocation Caffeine Cache"))
                .doOnSuccess(s -> log.debug("IPLocation Caffeine Cache Cleared"))
                .then()
                .onErrorMap(e -> new IPApplicationDataAccessException("Clear IPLocation Caffeine Cache failed"));
    }

}
