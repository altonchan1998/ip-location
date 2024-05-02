package com.vgt.ip.dataaccess.iplocation.adaptor;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.dataaccess.iplocation.mapper.IPLocationDataAccessMapper;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationCaffeineRepositoryImpl;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationMongoRepositoryImpl;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationRedisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;



@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRepositoryImpl implements IPLocationRepository {
    private final IPLocationRedisRepositoryImpl ipLocationRedisRepositoryImpl;
    private final IPLocationMongoRepositoryImpl ipLocationMongoRepositoryImpl;
    private final IPLocationCaffeineRepositoryImpl ipLocationCaffeineRepositoryImpl;

    private final IPLocationDataAccessMapper ipLocationDataAccessMapper;

    public void clearLocalCache() {
         ipLocationCaffeineRepositoryImpl.cleanAll()
                .subscribe();
    }


    @Override
    public Mono<IPLocationDTO> findByIPAndVersionNotLessThan(String ip, long version) {
        return ipLocationCaffeineRepositoryImpl.findIPLocationByIP(ip)
                .flatMap(it -> deleteFromCaffeineIfVersionLessThan(it, version))
                .switchIfEmpty(
                        ipLocationRedisRepositoryImpl.findByIP(ip)
                                .flatMap(it -> deleteFromRedisIfVersionLessThan(it, version))
                                .doOnNext(this::saveToCaffeine))
                .switchIfEmpty(
                        ipLocationMongoRepositoryImpl.findByIPAndVersionGreaterThanEqual(ip, version)
                                .doOnNext(this::saveToRedis)
                ).map(ipLocationDataAccessMapper::toIPLocationDTO);
    }

    private void saveToRedis(IPLocationMongoEntity entity) {
        ipLocationRedisRepositoryImpl.save(entity)
                .doOnSubscribe(s -> log.debug("Saving IPLocation of {} to Redis", entity.getIp()))
                .doOnSuccess(s -> log.debug("Saved IPLocation of {} to Redis", entity.getIp()))
                .subscribe();
    }

    private void saveToCaffeine(IPLocationMongoEntity entity) {
        ipLocationCaffeineRepositoryImpl.save(entity)
                .subscribe();
    }

    private Mono<IPLocationMongoEntity> deleteFromCaffeineIfVersionLessThan(IPLocationMongoEntity entity, long version) {
        return Mono.defer(() -> {
            if (entity.getVersion() < version) {
                log.debug("Entity version {} of IP {} is less than version {}", entity.getVersion(), entity.getIp(), version);
                ipLocationCaffeineRepositoryImpl.deleteByIP(entity.getIp()).subscribe();
                return Mono.empty();
            }

            return Mono.just(entity);
        });
    }

    private Mono<IPLocationMongoEntity> deleteFromRedisIfVersionLessThan(IPLocationMongoEntity entity, long version) {
        return Mono.defer(() -> {
            if (entity.getVersion() < version) {
                log.debug("Entity version {} of IP {} is less than version {}", entity.getVersion(), entity.getIp(), version);
                ipLocationRedisRepositoryImpl.deleteByIP(entity.getIp()).subscribe();
                return Mono.empty();
            }

            return Mono.just(entity);
        });
    }
}
