package com.vgt.ip.service.iplocation;

import com.vgt.ip.model.entity.IPLocation;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.vgt.ip.constant.CacheConstants.CACHE_MANAGER_IP_LOCATION;
import static com.vgt.ip.constant.CacheConstants.CACHE_NAME_IP_LOCATION;

public interface IPLocationService {
    Mono<IPLocation> getIPLocationByIP(String ip);

    @Cacheable(value = CACHE_NAME_IP_LOCATION, cacheManager = CACHE_MANAGER_IP_LOCATION)
    Mono<IPLocation> buildIPLocationLocalCache(String ip);

    Mono<List<IPLocation>> getIPLocationByIPBatch(List<String> ip);

    void saveIPLocation(IPLocation ipLocation);
}
