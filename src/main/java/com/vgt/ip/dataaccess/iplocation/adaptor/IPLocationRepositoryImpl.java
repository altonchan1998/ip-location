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

import java.util.List;

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
    public Mono<Void> removeByIPAndVersionLessThan(String ip, Long version) {
        return Mono.zip(
                ipLocationCaffeineRepositoryImpl.deleteByKey(ip),
                ipLocationRedisRepositoryImpl.deleteByIP(ip),
                ipLocationMongoRepositoryImpl.deleteByIpAndVersionLessThan(ip, version)
        ).then();
    }

    @Override
    public Mono<IPLocationDTO> findByIP(String ip) {
        return ipLocationCaffeineRepositoryImpl.findIPLocationByIP(ip)
                .switchIfEmpty(
                        ipLocationRedisRepositoryImpl.findByIP(ip)
                                .doOnNext(this::saveToCaffeine))
                .switchIfEmpty(
                        ipLocationMongoRepositoryImpl.findByIP(ip)
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
}
