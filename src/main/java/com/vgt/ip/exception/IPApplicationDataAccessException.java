package com.vgt.ip.exception;

public class IPApplicationDataAccessException  extends RuntimeException {

    public IPApplicationDataAccessException(String message) {
        super(message);
    }

    public IPApplicationDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}