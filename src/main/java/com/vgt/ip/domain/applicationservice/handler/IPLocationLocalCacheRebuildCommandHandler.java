package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationLocalCacheRebuildCommandHandler implements Handler<List<String>, Void> {

    private final IPLocationRepository ipLocationRepository;

    @Override
    public Void handle(List<String> ipList) {
        ipLocationRepository.rebuildLocalCache(ipList);
        return null;
    }
}
