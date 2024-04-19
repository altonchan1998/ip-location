package com.vgt.ip.service.localcache;

import com.vgt.ip.model.entity.IPLocation;
import com.vgt.ip.repository.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.vgt.ip.constant.CacheConstants.CACHE_MANAGER_IP_LOCATION;
import static com.vgt.ip.constant.CacheConstants.CACHE_NAME_IP_LOCATION;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocalCacheServiceImpl implements LocalCacheService {
    private final IPLocationRepository ipLocationRepository;

    @Cacheable(value = CACHE_NAME_IP_LOCATION, cacheManager = CACHE_MANAGER_IP_LOCATION)
    @Override
    public Mono<IPLocation> buildLocalCacheForIPLocation(String ip) {
        return ipLocationRepository.getIPLocationByIP(ip);
    }
}
