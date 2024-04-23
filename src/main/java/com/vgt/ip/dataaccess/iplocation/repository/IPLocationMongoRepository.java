package com.vgt.ip.dataaccess.iplocation.repository;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPLocationMongoRepository extends ReactiveCrudRepository<IPLocationMongoEntity, String> {
}
