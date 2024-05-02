package com.vgt.ip.dataaccess.iplocation.repository;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IPLocationMongoCrudRepository extends ReactiveCrudRepository<IPLocationMongoEntity, String> {
    Mono<IPLocationMongoEntity> findByIpAndVersionGreaterThanEqual(String ip, long version);
    Mono<Void> deleteByIpAndVersionLessThan(String ip, long version);
}
