package com.vgt.ip.dataaccess.iplocation.mapper;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
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
}
