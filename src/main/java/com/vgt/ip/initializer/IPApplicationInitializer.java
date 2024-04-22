package com.vgt.ip.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPApplicationInitializer implements CommandLineRunner {
    private final List<ApplicationInitializer> applicationInitializers;

    @Override
    public void run(String... args) throws Exception {
        applicationInitializers.forEach(initializer -> {
            log.info("Initializing {}", initializer.getName());
            initializer.run();
            log.info("Initialized {}", initializer.getName());
        });
    }
}
