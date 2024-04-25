package com.vgt.ip.application.initializer;

import com.vgt.ip.domain.applicationservice.port.input.IPLocationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LocalIPLocationVersionInitializer implements ApplicationInitializer {
    private final IPLocationApplicationService ipLocationApplicationService;

    @Override
    public void run() {
        ipLocationApplicationService.refreshLocalIPLocationVersion()
                .subscribe();
    }

    @Override
    public String getName() {
        return "LocalIPLocationVersionInitializer";
    }
}