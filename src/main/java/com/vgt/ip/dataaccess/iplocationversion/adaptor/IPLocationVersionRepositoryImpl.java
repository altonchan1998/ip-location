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

    private volatile Long ipLocationLocalVersion = -1L;

    @Override
    public void saveIPLocationLocalVersion(Long version) {
        ipLocationLocalVersion = version;
    }

    @Override
    public Long findIpLocationLocalVersion() {
        return ipLocationLocalVersion;
    }

    @Override
    public Mono<Long> findRemoteIPLocationVersion() {
        return ipLocationVersionRedisRepository.getRedisIPLocationVersion();
    }


}
