package com.vgt.ip.dataaccess.iplocation.mapper;

import com.vgt.ip.dataaccess.iplocation.entity.IPLocationDetailEntity;
import com.vgt.ip.dataaccess.iplocation.entity.IPLocationMongoEntity;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IPLocationDataAccessMapper {


    public IPLocationDTO toIPLocationDTO(IPLocationMongoEntity entity) {
        IPLocationDetailEntity detail = entity.getAllInfo().get(entity.getPrimaryInfo());
        return IPLocationDTO.builder()
                .ip(entity.getIp())
                .country(detail.getCountry())
                .region(detail.getRegion())
                .city(detail.getCity())
                .province(detail.getProvince())
                .district(detail.getDistrict())
                .isp(detail.getIsp())
                .lang(detail.getLang())
                .description(detail.getDescription())
                .isChinaRegion(detail.getIsChinaRegion())
                .region(detail.getRegion())
                .isoCode(detail.getIsoCode())
                .timeZone(detail.getTimeZone())
                .lib(detail.getLib())
                .build();
    }
}
