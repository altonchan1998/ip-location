package com.vgt.ip.config;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.vgt.ip.constant.CacheConstants.CACHE_MANAGER_IP_LOCATION;

@EnableCaching
@Configuration
public class LocalCacheConfig {
    @Bean(CACHE_MANAGER_IP_LOCATION)
    public CacheManager ipLocationCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().maximumSize(4000));
        caffeineCacheManager.setAsyncCacheMode(true);
        return caffeineCacheManager;
    }
}
