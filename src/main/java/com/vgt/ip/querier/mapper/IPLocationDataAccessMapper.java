package com.vgt.ip.querier.mapper;

import com.vgt.ip.querier.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.querier.dataaccess.iplocation.entity.IPLocationMongoEntity;
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
