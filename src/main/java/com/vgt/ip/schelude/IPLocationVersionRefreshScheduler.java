package com.vgt.ip.schelude;


import com.vgt.ip.repository.IPLocationVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationVersionRefreshScheduler {
    private final IPLocationVersionRepository ipLocationVersionRepository;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void refreshIPLocationData() {
        log.info("IPLocationVersion refreshing started");
        ipLocationVersionRepository.updateIPLocationVersion();
        log.info("IPLocationVersion refreshing completed");
    }
}