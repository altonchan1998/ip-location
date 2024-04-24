package com.vgt.ip.dataaccess.iplocation.repository;


import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
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
                .doOnError(e -> log.error("Find IPLocation of {} in Mongo failed", ip, e));
    }
}
