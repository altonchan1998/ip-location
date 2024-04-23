package com.vgt.ip.domain.applicationservice.port.output;

import com.vgt.ip.domain.applicationservice.dto.iplocation.IPLocationDTO;
import com.vgt.ip.domain.core.valueobject.IPAddressV4;
import reactor.core.publisher.Mono;


public interface IPLocationRepository {
    Mono<IPLocationDTO> findIPLocationByIPAddressV4(IPAddressV4 ipAddressV4);
}
