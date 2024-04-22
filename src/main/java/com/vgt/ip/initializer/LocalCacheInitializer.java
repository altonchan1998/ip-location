package com.vgt.ip.initializer;

import com.vgt.ip.service.localcache.LocalCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LocalCacheInitializer implements ApplicationInitializer {
    private final LocalCacheService localCacheService;
    private final List<String> ipList = List.of("127.0.0.1", "127.0.0.2");

    @Override
    public void run() {
        ipList.forEach(ip -> localCacheService.buildLocalCacheForIPLocation(ip).subscribe());
    }

    @Override
    public String getName() {
        return "LocalCacheInitializer";
    }
}
