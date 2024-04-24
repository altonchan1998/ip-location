package com.vgt.ip.dataaccess.iplocation.adaptor;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.dataaccess.iplocation.mapper.IPLocationDataAccessMapper;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationCaffeineRepositoryImpl;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationMongoRepositoryImpl;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationRedisRepositoryImpl;
import com.vgt.ip.domain.core.valueobject.IPAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRepositoryImpl implements IPLocationRepository {
    private final IPLocationRedisRepositoryImpl ipLocationRedisRepositoryImpl;
    private final IPLocationMongoRepositoryImpl ipLocationMongoRepositoryImpl;
    private final IPLocationCaffeineRepositoryImpl ipLocationCaffeineRepository;

    private final IPLocationDataAccessMapper ipLocationDataAccessMapper;

    @Override
    public void rebuildLocalCache(List<String> ipList) {
        log.info("Rebuilding local cache");
        ipLocationCaffeineRepository.cleanAll();
        ipList.forEach(
                ip -> ipLocationMongoRepositoryImpl.findByIP(ip)
                        .subscribe(ipLocationCaffeineRepository::save)
        );
        log.info("Local cache rebuilt");
    }

    @Override
    public Mono<IPLocationDTO> findByIPAddress(IPAddress ipAddress) {
        if (Objects.isNull(ipAddress)) {
            throw new IllegalArgumentException("ipAddress cannot be null");
        }

        String ip = ipAddress.getIp();
        return ipLocationCaffeineRepository.findIPLocationByIP(ip)
                .switchIfEmpty(ipLocationRedisRepositoryImpl.findByIP(ip))
                .switchIfEmpty(ipLocationMongoRepositoryImpl.findByIP(ip).flatMap(this::saveToRedis))
                .map(ipLocationDataAccessMapper::toIPLocationDTO);
    }

    private Mono<IPLocationMongoEntity> saveToRedis(IPLocationMongoEntity entity) {
        log.info("Saving {} to Redis", entity.getIp());
        return ipLocationRedisRepositoryImpl.save(entity)
                .then(Mono.just(entity));
    }
}
