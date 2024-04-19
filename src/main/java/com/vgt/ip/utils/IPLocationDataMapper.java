package com.vgt.ip.utils;

import com.vgt.ip.constant.ResponseConstants;
import com.vgt.ip.model.dto.iplocation.IPLocationRequest;
import com.vgt.ip.model.dto.iplocation.IPLocationResponse;
import com.vgt.ip.model.dto.Result;
import com.vgt.ip.model.entity.IPLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.vgt.ip.constant.ResponseConstants.RESPONSE_MESSAGE_SUCCESS;

@Slf4j
@Component
public class IPLocationDataMapper {
    public Result<IPLocationResponse> toResult(IPLocation ipLocation) {
        IPLocationResponse data = toResponse(ipLocation);
        return new Result<>(ResponseConstants.RESPONSE_CODE_OK, data, RESPONSE_MESSAGE_SUCCESS);
    }

    public Result<List<IPLocationResponse>> toResult(List<IPLocation> ipLocations) {
        List<IPLocationResponse> data = toResponse(ipLocations);
        return new Result<>(ResponseConstants.RESPONSE_CODE_OK, data, RESPONSE_MESSAGE_SUCCESS);
    }

    public IPLocationResponse toResponse(IPLocation location) {
        return IPLocationResponse.builder()
                .ip(location.getIp())
                .country(location.getCountry())
                .countryCode(location.getCountryCode())
                .region(location.getRegion())
                .regionName(location.getRegionName())
                .city(location.getCity())
                .zip(location.getZip())
                .lat(location.getLat())
                .lon(location.getLon())
                .timezone(location.getTimezone())
                .isp(location.getIsp())
                .org(location.getOrg())
                .query(location.getQuery())
                .build();
    }

    public List<IPLocationResponse> toResponse(List<IPLocation> location) {
        return location.stream()
                .map(this::toResponse)
                .toList();
    }

    public IPLocation toIPLocation(IPLocationRequest request) {
        return IPLocation.builder()
                .ip(request.getIp())
                .country(request.getCountry())
                .countryCode(request.getCountryCode())
                .region(request.getRegion())
                .regionName(request.getRegionName())
                .city(request.getCity())
                .zip(request.getZip())
                .lat(request.getLat())
                .lon(request.getLon())
                .timezone(request.getTimezone())
                .isp(request.getIsp())
                .org(request.getOrg())
                .query(request.getQuery())
                .build();
    }
}
