package com.vgt.ip.dataaccess.iplocation.repository;


import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.exception.IPApplicationDataAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Repository
public class IPLocationMongoRepositoryImpl {
    private final IPLocationMongoCrudRepository ipLocationMongoCrudRepository;

    public Mono<IPLocationMongoEntity> findByIP(String ip) {
        return ipLocationMongoCrudRepository.findByIp(ip)
                .doOnSubscribe(s -> log.debug("Finding IPLocation of {} in Mongo", ip))
                .onErrorMap(e -> new IPApplicationDataAccessException("Find IPLocation of " + ip + " in Mongo failed"));
    }

    public Mono<Void> deleteByIpAndVersionLessThan(String ip, Long version) {
        return ipLocationMongoCrudRepository.deleteByIpAndVersionLessThan(ip, version);
    }
}
