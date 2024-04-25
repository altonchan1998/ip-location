package com.vgt.ip.domain.applicationservice.port.input;


import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.handler.IPLocationLocalCacheBuildCommandHandler;
import com.vgt.ip.domain.applicationservice.handler.IPLocationLocalCacheRebuildCommandHandler;
import com.vgt.ip.domain.applicationservice.handler.IPLocationQueryHandler;
import com.vgt.ip.domain.applicationservice.handler.IPLocationVersionRefreshCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@RequiredArgsConstructor
@Service
public class IPLocationApplicationServiceImpl implements IPLocationApplicationService {
    private final IPLocationQueryHandler ipLocationQueryHandler;
    private final IPLocationVersionRefreshCommandHandler versionRefreshCommandHandler;
    private final IPLocationLocalCacheRebuildCommandHandler ipLocationLocalCacheRebuildCommandHandler;
    private final IPLocationLocalCacheBuildCommandHandler ipLocationLocalCacheBuildCommandHandler;

    private static final AtomicBoolean lock = new AtomicBoolean();

    @Override
    public Mono<Result<IPLocationResponse>> getIPLocationByIP(IPLocationQuery ipLocationQuery) {
        return ipLocationQueryHandler.handle(ipLocationQuery);
    }

    @Override
    public Mono<Void> refreshLocalIPLocationVersion() {
        return versionRefreshCommandHandler.handle(null)
                .doOnSubscribe(unused -> log.info("Local IPLocationVersion refresh started"))
                .doOnSuccess(unused -> log.info("Local IPLocationVersion refresh finished"))
                .doOnError(error -> log.error("Local IPLocationVersion refresh failed", error));
    }

    @Override
    public Mono<Void> buildIPLocationLocalCache(List<String> ipList) {
        return ipLocationLocalCacheBuildCommandHandler.handle(ipList)
                .doOnSubscribe(unused -> log.info("Build IPLocation Local Cache started"))
                .doOnSuccess(unused -> log.info("Build IPLocation Local Cache finished"))
                .doOnError(error -> log.error("Build IPLocation Local Cache failed", error));

    }

}
