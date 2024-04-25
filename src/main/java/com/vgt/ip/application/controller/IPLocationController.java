package com.vgt.ip.application.controller;


import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.port.input.IPLocationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IPLocationController {
    private final IPLocationApplicationService ipLocationApplicationService;

    @GetMapping("/ipLocation")
    public Mono<Result<IPLocationResponse>> getIPLocationByIP(@RequestParam(name = "ip") String ip) {
        return ipLocationApplicationService.getIPLocationByIP(new IPLocationQuery(ip))
                .doOnSubscribe(result -> log.info("getIPLocationByIP: {}", ip));
    }
}
