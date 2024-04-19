package com.vgt.ip.exception;

public class IPLocationNotFoundException extends RuntimeException {

    public IPLocationNotFoundException(String message) {
        super(message);
    }

    public IPLocationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
