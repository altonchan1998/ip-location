package com.vgt.ip.dataaccess.iplocation.adaptor;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.dataaccess.iplocation.mapper.IPLocationDataAccessMapper;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationCaffeineRepositoryImpl;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationMongoRepositoryImpl;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationRedisRepositoryImpl;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.domain.core.valueobject.IPAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRepositoryImpl implements IPLocationRepository {
    private final IPLocationRedisRepositoryImpl ipLocationRedisRepositoryImpl;
    private final IPLocationMongoRepositoryImpl ipLocationMongoRepositoryImpl;
    private final IPLocationCaffeineRepositoryImpl ipLocationCaffeineRepositoryImpl;

    private final IPLocationDataAccessMapper ipLocationDataAccessMapper;

    public Mono<Void> clearLocalCache() {
        log.info("Rebuilding local cache");
        return ipLocationCaffeineRepositoryImpl.cleanAll();
    }

    @Override
    public Mono<IPLocationDTO> findByIPAddress(IPAddress ipAddress) {
        if (Objects.isNull(ipAddress)) {
            throw new IllegalArgumentException("ipAddress cannot be null");
        }

        String ip = ipAddress.getIp();
        return ipLocationCaffeineRepositoryImpl.findIPLocationByIP(ip)
                .switchIfEmpty(ipLocationRedisRepositoryImpl.findByIP(ip))
                .switchIfEmpty(ipLocationMongoRepositoryImpl.findByIP(ip).doOnNext(this::saveToRedis))
                .map(ipLocationDataAccessMapper::toIPLocationDTO)
                .doOnError(e -> log.error("Find IPLocation of {} failed: {}", ip, e.getMessage()));
    }

    @Override
    public Mono<List<Void>> buildLocalCache(List<String> ipList) {
        return Flux.fromIterable(ipList)
                .flatMap(ipLocationRedisRepositoryImpl::findByIP)
                .flatMap(ipLocationCaffeineRepositoryImpl::save)
                .collectList();
    }

    private void saveToRedis(IPLocationMongoEntity entity) {
        ipLocationRedisRepositoryImpl.save(entity)
                .doOnSubscribe(s -> log.debug("Saving IPLocation of {} to Redis", entity.getIp()))
                .doOnSuccess(s -> log.debug("Saved IPLocation of {} to Redis", entity.getIp()))
                .subscribe();
    }
}
