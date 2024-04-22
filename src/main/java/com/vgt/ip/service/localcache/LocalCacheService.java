package com.vgt.ip.service.localcache;

import com.vgt.ip.model.entity.IPLocation;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Mono;

import static com.vgt.ip.constant.CacheConstants.CACHE_MANAGER_IP_LOCATION;
import static com.vgt.ip.constant.CacheConstants.CACHE_NAME_IP_LOCATION;

public interface LocalCacheService {
    Mono<IPLocation> buildLocalCacheForIPLocation(String ip);
}
