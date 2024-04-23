package com.vgt.ip.dataaccess.iplocation.adaptor;

import com.vgt.ip.dataaccess.iplocation.repository.IPLocationMongoRepository;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.port.output.IPLocationRepository;
import com.vgt.ip.dataaccess.iplocation.repository.IPLocationRedisRepository;
import com.vgt.ip.domain.core.valueobject.IPAddressV4;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.vgt.ip.constant.CacheConstants.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationRepositoryImpl implements IPLocationRepository {
    private final IPLocationRedisRepository ipLocationRedisRepository;
    private final IPLocationMongoRepository ipLocationMongoRepository;

    @Override
    public Mono<IPLocationDTO> findIPLocationByIPAddressV4(IPAddressV4 ipAddressV4) {
        if (Objects.isNull(ipAddressV4)) {
            throw new IllegalArgumentException("ipAddressV4 cannot be null");
        }
        String ip = ipAddressV4.getIp();

        return ipLocationRedisRepository.findIPLocationByIP(ipAddressV4.getIp())
                .switchIfEmpty(() -> ipLocationMongoRepository.findIPLocationByIP(ipAddressV4.getIp()));
    }

}
