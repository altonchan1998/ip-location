package com.vgt.ip.application.initializer;

import com.vgt.ip.domain.applicationservice.port.input.IPLocationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LocalCacheInitializer implements ApplicationInitializer {
    private final IPLocationApplicationService ipLocationApplicationService;
    private final List<String> ipList = List.of("127.0.0.1", "127.0.0.2");

    @Override
    public void run() {
        ipLocationApplicationService.ipLocationLocalCacheRebuildCommand(ipList);
    }

    @Override
    public String getName() {
        return "LocalCacheInitializer";
    }
}
