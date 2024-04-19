package com.vgt.ip.repository;

import com.vgt.ip.model.entity.IPLocation;
import com.vgt.ip.repository.redis.IPLocationRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRepositoryImpl implements IPLocationRepository {
    private final IPLocationRedisRepository ipLocationRedisRepository;

    @Override
    public Mono<IPLocation> getIPLocationByIP(String ip) {
        return ipLocationRedisRepository.getIPLocationByIP(ip);
    }

    @Override
    public Mono<List<IPLocation>> getIPLocationByIPBatch(List<String> ipList) {
        return ipLocationRedisRepository.getIPLocationByIPBatch(ipList);
    }

    @Override
    public void saveIPLocation(String key, IPLocation ipLocation) {
        ipLocationRedisRepository.saveIPLocation(ipLocation);
    }
}
