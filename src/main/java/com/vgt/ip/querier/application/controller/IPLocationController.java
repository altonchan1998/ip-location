package com.vgt.ip.querier.application.controller;


import com.vgt.ip.querier.applicationservice.dto.Result;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.querier.applicationservice.port.input.IPLocationApplicationService;
import com.vgt.ip.querier.applicationservice.port.input.IPLocationBackwardCompatibleService;
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
    private final IPLocationBackwardCompatibleService ipLocationBackwardCompatibleService;

    @GetMapping("/ipLocation")
    public Mono<Result<IPLocationResponse>> getIPLocationByIP(@RequestParam(name = "ip") String ip) {
//        return ipLocationApplicationService.getIPLocationByIP(new IPLocationQuery(ip))
//                .doOnSubscribe(result -> log.info("getIPLocationByIP: {}", ip));
        return Mono.empty();
    }

    @GetMapping("/getIp")
    public Mono<Result<Object>> getIp(
            @RequestParam(name = "ip", required = false) List<String> ipList,
            @RequestHeader Map<String, String> headers
    ) {
        return ipLocationBackwardCompatibleService.getIp(
                IPLocationQuery.builder()
                        .headers(headers)
                        .ipList(ipList)
                        .build()
        );
    }
}
