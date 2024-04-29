package com.vgt.ip.domain.applicationservice.handler;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import com.vgt.ip.domain.applicationservice.mapper.IPLocationApplicationServiceDataMapper;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import com.vgt.ip.exception.IPLocationNotFoundException;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.BiPredicate;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationQueryHandler implements Handler<IPLocationQuery, Result<IPLocationResponse>> {

    private final IPLocationApplicationServiceDataMapper ipLocationApplicationServiceDataMapper;
    private final IPLocationRepository ipLocationRepository;
    private final IPLocationVersionRepository ipLocationVersionRepository;

    public Mono<Result<IPLocationResponse>> handle(IPLocationQuery query) {
        // TODO: Test Behaviour
        return ipLocationRepository.findByIP(query.getIp())
                .switchIfEmpty(Mono.error(new IPLocationNotFoundException(query.getIp())))
                .filter(it -> isVersionValid.test(it.getVersion(), ipLocationVersionRepository.findLocalIpLocationVersion()))
                .map(ipLocationApplicationServiceDataMapper::toResult)
                .switchIfEmpty(deleteOutdatedRecordAndFindByIP(query.getIp()))
                .doOnSuccess(result -> log.debug("Handle IPLocationQuery success: {}", result));
    }

    private Mono<Result<IPLocationResponse>> deleteOutdatedRecordAndFindByIP(String ip) {
        return ipLocationRepository.removeByIPAndVersionLessThan(ip, ipLocationVersionRepository.findLocalIpLocationVersion())
                .then(ipLocationRepository.findByIP(ip))
                .map(ipLocationApplicationServiceDataMapper::toResult);
    }

    private final BiPredicate<Long, Long> isVersionValid = (dataVersion, localVersion) -> dataVersion >= localVersion;

}
