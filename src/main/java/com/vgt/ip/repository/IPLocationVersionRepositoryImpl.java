package com.vgt.ip.repository;


import com.vgt.ip.repository.redis.IPLocationVersionRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationVersionRepositoryImpl implements IPLocationVersionRepository {
    private final IPLocationVersionRedisRepository ipLocationVersionRedisRepository;

    @Override
    public Long getIPLocationVersion() {
        return ipLocationVersionRedisRepository.getIPLocationVersion();
    }

    @Override
    public void updateIPLocationVersion() {
        ipLocationVersionRedisRepository.updateIPLocationVersion();
    }
}
