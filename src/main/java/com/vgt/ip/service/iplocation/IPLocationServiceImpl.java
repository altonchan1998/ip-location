package com.vgt.ip.service.iplocation;

import com.vgt.ip.exception.IPLocationNotFoundException;
import com.vgt.ip.model.entity.IPLocation;
import com.vgt.ip.repository.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.List;

import static com.vgt.ip.constant.CacheConstants.CACHE_CONDITION_NO_CACHE;
import static com.vgt.ip.constant.CacheConstants.CACHE_MANAGER_IP_LOCATION;
import static com.vgt.ip.constant.CacheConstants.CACHE_NAME_IP_LOCATION;


@Slf4j
@RequiredArgsConstructor
@Service
public class IPLocationServiceImpl implements IPLocationService {
    private final IPLocationRepository ipLocationRepository;

    @Cacheable(value = CACHE_NAME_IP_LOCATION, cacheManager = CACHE_MANAGER_IP_LOCATION, condition = CACHE_CONDITION_NO_CACHE)
    @Override
    public Mono<IPLocation> getIPLocationByIP(String ip) {
        return ipLocationRepository.getIPLocationByIP(ip)
                .switchIfEmpty(Mono.error(new IPLocationNotFoundException(ip)));
    }

    @Cacheable(value = CACHE_NAME_IP_LOCATION, cacheManager = CACHE_MANAGER_IP_LOCATION)
    @Override
    public Mono<IPLocation> buildIPLocationLocalCache(String ip) {
        return ipLocationRepository.getIPLocationByIP(ip)
                .switchIfEmpty(Mono.error(new IPLocationNotFoundException(ip)));
    }

    @Override
    public Mono<List<IPLocation>> getIPLocationByIPBatch(List<String> ipList) {
        return ipLocationRepository.getIPLocationByIPBatch(ipList)
                .switchIfEmpty(Mono.error(new IPLocationNotFoundException(ipList.toString())));
    }

    @Override
    public void saveIPLocation(IPLocation ipLocation) {
        ipLocationRepository.saveIPLocation(ipLocation.getIp(), ipLocation);
    }
}
