package com.vgt.ip.dataaccess.iplocationversion.adaptor;


import com.vgt.ip.domain.applicationservice.port.output.IPLocationVersionRepository;
import com.vgt.ip.dataaccess.iplocationversion.repository.IPLocationVersionRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionRepositoryImpl implements IPLocationVersionRepository {
    private final IPLocationVersionRedisRepository ipLocationVersionRedisRepository;

    @Override
    public Mono<Long> getIPLocationVersion() {
        return ipLocationVersionRedisRepository.getRedisIPLocationVersion();
    }
}
