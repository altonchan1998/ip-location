package com.vgt.ip.application.scheduler;


import com.vgt.ip.domain.applicationservice.port.input.IPLocationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationVersionRefreshScheduler {
    private final IPLocationApplicationService ipLocationApplicationService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS, initialDelay = 5)
    public void run() {
        ipLocationApplicationService.refreshLocalIPLocationVersion().subscribe();
    }
}