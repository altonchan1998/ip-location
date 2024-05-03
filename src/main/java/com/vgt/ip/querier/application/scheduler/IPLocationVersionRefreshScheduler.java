package com.vgt.ip.querier.application.scheduler;


import com.vgt.ip.querier.applicationservice.port.input.IPLocationApplicationService;
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

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void run() {
        ipLocationApplicationService.refreshLocalIPLocationVersion()
                .subscribe();
    }
}