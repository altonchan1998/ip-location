package com.vgt.ip.dataaccess.iplocation;

import com.vgt.ip.model.entity.IPLocation;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.vgt.ip.constant.CacheConstants.CACHE_NAME_IP_LOCATION;

public interface IPLocationService {
    Mono<IPLocation> getIPLocationByIP(String ip);

    Mono<IPLocation> buildIPLocationLocalCache(String ip);

    Mono<List<IPLocation>> getIPLocationByIPBatch(List<String> ip);

    void saveIPLocation(IPLocation ipLocation);
}
