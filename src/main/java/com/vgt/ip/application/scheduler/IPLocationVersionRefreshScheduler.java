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

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void run() {
        ipLocationApplicationService.refreshLocalIPLocationVersion()
                .doOnSubscribe(unused -> log.info("Local IPLocationVersion refresh started"))
                .doOnSuccess(unused -> log.info("Local IPLocationVersion refresh finished"))
                .doOnError(error -> log.error("Local IPLocationVersion refresh failed", error))
                .subscribe();
    }
}