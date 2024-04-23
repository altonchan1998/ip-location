package com.vgt.ip.dataaccess.iplocation.adaptor;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.dataaccess.iplocation.mapper.IPLocationDataAccessMapper;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationCaffeineRepository;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationMongoRepository;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationRedisRepository;
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
    private final IPLocationRedisRepository ipLocationRedisRepository;
    private final IPLocationMongoRepository ipLocationMongoRepository;
    private final IPLocationCaffeineRepository ipLocationCaffeineRepository;

    private final IPLocationDataAccessMapper ipLocationDataAccessMapper;

    @Override
    public void rebuildLocalCache(List<String> ipList) {
        ipLocationCaffeineRepository.cleanAll();
        ipList.forEach(
                ip -> ipLocationMongoRepository.findById(ip)
                        .subscribe(
                                it -> ipLocationCaffeineRepository.save(
                                        ipLocationDataAccessMapper.toIPLocationCaffeineEntity(it)
                                )
                        )
        );
    }

    @Override
    public Mono<IPLocationDTO> findByIPAddress(IPAddress ipAddress) {
        if (Objects.isNull(ipAddress)) {
            throw new IllegalArgumentException("ipAddress cannot be null");
        }

        String ip = ipAddress.getIp();

        return ipLocationCaffeineRepository.findIPLocationByIP(ip)
                .map(ipLocationDataAccessMapper::toIPLocationDTO)
                .switchIfEmpty(ipLocationRedisRepository.findByIP(ip)
                .map(ipLocationDataAccessMapper::toIPLocationDTO))
                .switchIfEmpty(ipLocationMongoRepository.findById(ip)
                .flatMap(this::saveToRedis)
                .map(ipLocationDataAccessMapper::toIPLocationDTO));
    }

    private Mono<IPLocationMongoEntity> saveToRedis(IPLocationMongoEntity entity) {
        ipLocationRedisRepository.save(ipLocationDataAccessMapper.toIPLocationRedisEntity(entity));
        return Mono.just(entity);
    }
}
