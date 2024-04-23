package com.vgt.ip.dataaccess.iplocation.mapper;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationCaffeineEntity;
import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.dataaccess.iplocation.entity.IPLocationRedisEntity;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IPLocationDataAccessMapper {


    public IPLocationDTO toIPLocationDTO(IPLocationMongoEntity entity) {
        return IPLocationDTO.builder()
                .build();
    }

    public IPLocationDTO toIPLocationDTO(IPLocationRedisEntity entity) {
        return IPLocationDTO.builder()
                .build();
    }

    public IPLocationDTO toIPLocationDTO(IPLocationCaffeineEntity entity) {
        return IPLocationDTO.builder()
                .build();
    }

    public IPLocationRedisEntity toIPLocationRedisEntity(IPLocationMongoEntity entity) {
        return IPLocationRedisEntity.builder()
                .build();
    }

    public IPLocationCaffeineEntity toIPLocationCaffeineEntity(IPLocationMongoEntity entity) {
        return IPLocationCaffeineEntity.builder()
                .build();
    }
}
