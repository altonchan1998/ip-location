package com.vgt.ip.querier.constant;

import java.time.Duration;

public class RedisConstants {
    private RedisConstants() {
    }

    public static final String REDIS_KEY_PREFIX_IP_LOCATION = "ip::location::";
    public static final String REDIS_KEY_IP_LOCATION_VERSION = "ip::location::version";
    public static final Duration REDIS_TTL_DURATION_DEFAULT = Duration.ofMinutes(30L);
}
