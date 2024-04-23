package com.vgt.ip.domain.applicationservice.port.input;


import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.handler.IPLocationQueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class IPLocationApplicationServiceImpl implements IPLocationApplicationService {
    private final IPLocationQueryHandler ipLocationQueryHandler;

    @Override
    public void refreshLocalIPLocation() {
        log.info("Refresh local IPLocation");
    }

    @Override
    public Mono<Result<IPLocationResponse>> getIPLocationByIP(IPLocationQuery ipLocationQuery) {
        return ipLocationQueryHandler.handle(ipLocationQuery);
    }

}
