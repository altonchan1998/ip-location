package com.vgt.ip.domain.applicationservice.mapper;

import com.vgt.ip.constant.ResponseConstants;
import com.vgt.ip.domain.applicationservice.dto.Result;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationQuery;
import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.vgt.ip.constant.ResponseConstants.RESPONSE_MESSAGE_SUCCESS;

@Slf4j
@Component
public class IPLocationApplicationServiceDataMapper {
    public Result<IPLocationResponse> toResult(IPLocationDTO ipLocation) {
        IPLocationResponse data = toResponse(ipLocation);
        return new Result<>(ResponseConstants.RESPONSE_CODE_OK, data, RESPONSE_MESSAGE_SUCCESS);
    }

    public Result<List<IPLocationResponse>> toResult(List<IPLocationDTO> ipLocations) {
        List<IPLocationResponse> data = toResponse(ipLocations);
        return new Result<>(ResponseConstants.RESPONSE_CODE_OK, data, RESPONSE_MESSAGE_SUCCESS);
    }

    public IPLocationResponse toResponse(IPLocationDTO location) {
        return IPLocationResponse.builder()
                .ip(location.getIp())
                .country(location.getCountry())
                .province(location.getProvince())
                .city(location.getCity())
                .region(location.getRegion())
                .district(location.getDistrict())
                .isp(location.getIsp())
                .description(location.getDescription())
                .lang(location.getLang())
                .isChinaRegion(location.getIsChinaRegion())
                .vd001ThirdPayEnabled(location.getVd001ThirdPayEnabled())
                .isoCode(location.getIsoCode())
                .timeZone(location.getTimeZone())
                .region(location.getRegion())
                .build();
    }

    public List<IPLocationResponse> toResponse(List<IPLocationDTO> location) {
        return location.stream()
                .map(this::toResponse)
                .toList();
    }

    public IPLocationDTO toIPLocation(IPLocationQuery request) {
        return IPLocationDTO.builder()
                .build();
    }
}
