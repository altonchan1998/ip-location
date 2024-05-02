package com.vgt.ip.domain.applicationservice.port.input;

import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.helper.IPHelper;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Component
public class IPLocationBackwardCompatibleServiceImpl implements IPLocationBackwardCompatibleService {

    private final IPHelper dataMapper;
    private final IPLocationRepository ipLocationRepository;
    private final IPLocationVersionRepository ipLocationVersionRepository;

    private final IPHelper ipHelper;

    @Override
    public Mono<Result<Object>> getIp(IPLocationQuery query) {
        return dataMapper.toIPFlux(query.getHeaders(), query.getIpList())
                .flatMapSequential(this::emptyMapOrIPInfo)
                .collectList()
                .map(
                        it -> new Result<>(
                                1,
                                shouldOutputInList(query) ? it : it.get(0),
                                "success"
                        )
                );
    }

    private Mono<Object> emptyMapOrIPInfo(String ip) {
        return Mono.defer(() -> {
            if (!ipHelper.isValidIP(ip)) {
                log.warn("Invalid IP: {}", ip);
                return Mono.just(Collections.emptyMap());
            }

            return ipLocationRepository.findByIPAndVersionNotLessThan(
                            ip,
                            ipLocationVersionRepository.findLocalIpLocationVersion()
                    )
                    .map(it -> (Object) it)
                    .defaultIfEmpty(Collections.emptyMap());
        });
    }

    private boolean shouldOutputInList(IPLocationQuery query) {
        return CollectionUtils.isNotEmpty(query.getIpList());
    }
}
