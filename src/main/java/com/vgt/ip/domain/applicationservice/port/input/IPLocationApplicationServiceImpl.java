package com.vgt.ip.domain.applicationservice.port.input;


import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.handler.IPLocationLocalCacheRebuildCommandHandler;
import com.vgt.ip.domain.applicationservice.handler.IPLocationQueryHandler;
import com.vgt.ip.domain.applicationservice.handler.IPLocationVersionRefreshCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class IPLocationApplicationServiceImpl implements IPLocationApplicationService {
    private final IPLocationQueryHandler ipLocationQueryHandler;
    private final IPLocationVersionRefreshCommandHandler versionRefreshCommandHandler;
    private final IPLocationLocalCacheRebuildCommandHandler ipLocationLocalCacheRebuildCommandHandler;


    @Override
    public Mono<Result<IPLocationResponse>> getIPLocationByIP(IPLocationQuery ipLocationQuery) {
        return ipLocationQueryHandler.handle(ipLocationQuery);
    }

    @Override
    public void refreshLocalIPLocationVersion() {
        versionRefreshCommandHandler.handle(null);
    }

    @Override
    public void rebuildIPLocationLocalCache(List<String> ip) {
        ipLocationLocalCacheRebuildCommandHandler.handle(ip);
    }

}
