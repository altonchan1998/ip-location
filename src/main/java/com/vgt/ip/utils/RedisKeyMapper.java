package com.vgt.ip.utils;

import org.springframework.stereotype.Component;

import java.util.List;

import static com.vgt.ip.constant.RedisConstants.REDIS_KEY_PREF_IP_LOCATION;

@Component
public class RedisKeyMapper {
    public String toIPLocationKey(String ip) {
        return REDIS_KEY_PREF_IP_LOCATION + ip;
    }
    public List<String> toIPLocationKeys(List<String> ip) {
        return ip.stream()
                .map(this::toIPLocationKey)
                .toList();
    }
}
