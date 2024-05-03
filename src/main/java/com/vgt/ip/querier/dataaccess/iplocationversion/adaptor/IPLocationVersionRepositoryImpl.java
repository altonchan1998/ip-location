package com.vgt.ip.querier.dataaccess.iplocationversion.adaptor;


import com.vgt.ip.querier.applicationservice.port.output.IPLocationVersionRepository;
import com.vgt.ip.querier.dataaccess.iplocationversion.repository.IPLocationVersionRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionRepositoryImpl implements IPLocationVersionRepository {
    private final IPLocationVersionRedisRepository ipLocationVersionRedisRepository;

    private volatile long ipLocationLocalVersion = -1L;

    @Override
    public void saveLocalIPLocationVersion(long version) {
        ipLocationLocalVersion = version;
    }

    @Override
    public Long findLocalIpLocationVersion() {
        return ipLocationLocalVersion;
    }

    @Override
    public Mono<Long> findRemoteIPLocationVersion() {
        return ipLocationVersionRedisRepository.getRedisIPLocationVersion();
    }
}
