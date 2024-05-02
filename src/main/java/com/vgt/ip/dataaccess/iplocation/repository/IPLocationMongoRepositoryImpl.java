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

    public Mono<IPLocationMongoEntity> findByIPAndVersionGreaterThanEqual(String ip, long version) {
        return ipLocationMongoCrudRepository.findByIpAndVersionGreaterThanEqual(ip, version)
                .doOnSubscribe(s -> log.debug("Finding IPLocation of IP: {} and Version: {} in Mongo", ip, version))
                .doOnSuccess(s -> log.debug("Found IPLocation of IP: {} and Version: {} in Mongo", ip, version))
                .onErrorMap(e -> new IPApplicationDataAccessException("Find IPLocation of IP: " + ip + " and Version: " + version + " in Mongo failed"));
    }

    public Mono<Void> deleteByIpAndVersionLessThan(String ip, long version) {
        return ipLocationMongoCrudRepository.deleteByIpAndVersionLessThan(ip, version)
                .doOnSubscribe(s -> log.debug("Deleting IPLocation of IP: {} and Version: {} in Mongo", ip, version))
                .doOnSuccess(s -> log.debug("Deleted IPLocation of IP: {} and Version: {} in Mongo", ip, version))
                .onErrorMap(e -> new IPApplicationDataAccessException("Delete IPLocation of IP: " + ip + " and Version: " + version + " in Mongo failed"));
    }
}
