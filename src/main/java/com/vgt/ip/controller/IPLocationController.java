package com.vgt.ip.controller;

import com.vgt.ip.model.dto.iplocation.IPLocationRequest;
import com.vgt.ip.mapper.IPLocationDataMapper;
import com.vgt.ip.model.dto.iplocation.IPLocationResponse;
import com.vgt.ip.model.dto.Result;
import com.vgt.ip.service.iplocation.IPLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IPLocationController {
    private final IPLocationService ipLocationService;
    private final IPLocationDataMapper ipLocationDataMapper;

    @GetMapping("/ipLocation")
    public Mono<Result<IPLocationResponse>> getIPLocationByIP(
            @RequestParam(name = "ip") String ip
    ) {
        log.info("getIPLocationByIP: {}", ip);
        return ipLocationService.getIPLocationByIP(ip)
                .map(ipLocationDataMapper::toResult);
    }

    @GetMapping("/ipLocation/batch")
    public Mono<Result<List<IPLocationResponse>>> getIPLocationByIPBatch(
            @RequestParam(name = "ip") List<String> ipList
    ) {
        return ipLocationService.getIPLocationByIPBatch(ipList)
                .map(ipLocationDataMapper::toResult);
    }

    @PostMapping("/ipLocation")
    public void saveIPLocation(
            @RequestBody IPLocationRequest request
    ) {
        ipLocationService.saveIPLocation(ipLocationDataMapper.toIPLocation(request));
    }
}
