package com.vgt.ip.application.scheduler;


import com.vgt.ip.dataaccess.iplocationversion.IPLocationVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationVersionRefreshScheduler {
    private final IPLocationVersionService ipLocationVersionService;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void run() {
        log.info("Local IPLocationVersion refreshing started");
        ipLocationVersionService.refreshLocalIPLocationVersion();
        log.info("Local IPLocationVersion refreshing completed");
    }
}