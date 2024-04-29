package com.vgt.ip.application.controller;


import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.port.input.IPLocationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IPLocationController {
    private final IPLocationApplicationService ipLocationApplicationService;

    @GetMapping("/ipLocation")
    public Mono<Result<?>> getIPLocationByIP(@RequestParam(name = "ip", required = false) List<String> ip,
                                             @RequestHeader Map<String, String> headers) {
        return ipLocationApplicationService.getIPLocationByIP(IPLocationQuery.builder().headers(headers).ips(ip).build());
    }
}
