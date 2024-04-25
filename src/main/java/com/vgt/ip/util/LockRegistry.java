package com.vgt.ip.util;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;

public class LockRegistry {
    private LockRegistry() {}

    @Getter
    public static final AtomicBoolean ipLocationCacheLock = new AtomicBoolean();


}
