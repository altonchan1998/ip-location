package com.vgt.ip.querier.applicationservice.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Component
public class IPHelper {

    private static final String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    private static final Pattern pattern = Pattern.compile(regex);
    private static final String defaultIP = "127.0.0.1";

    public Flux<String> toIPFlux(Map<String, String> headers, List<String> ipList) {
        log.info("toIPFlux: {}", ipList);
        log.info("headers: {}", headers);
        return Flux.fromIterable(ipList)
                .switchIfEmpty(getIpFromHeader(headers, "true-client-ip"))
                .switchIfEmpty(getIpFromHeader(headers, "x-original-forwarded-for"))
                .switchIfEmpty(getIpFromHeader(headers, "x-forwarded-for"))
                .switchIfEmpty(Flux.defer(() -> Flux.just(defaultIP)));
    }

    private Flux<String> getIpFromHeader(Map<String, String> headers, String name) {
        String ipAddress = headers.get(name);
        if (ipAddress != null && !ipAddress.isEmpty()) {
            String[] ipList = headers.get(name).split(",");
            return Flux.just(ipList[0].trim());
        } else {
            return Flux.empty();
        }
    }

    public boolean isValidIP(String ip) {
        return pattern.matcher(ip).matches();
    }


}
