package com.vgt.ip.repository;

import com.vgt.ip.model.entity.IPLocation;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPLocationRepository {
    Mono<IPLocation> getIPLocationByIP(String ip);

    Mono<List<IPLocation>> getIPLocationByIPBatch(List<String> ipList);

    void saveIPLocation(String key, IPLocation ipLocation);
}
