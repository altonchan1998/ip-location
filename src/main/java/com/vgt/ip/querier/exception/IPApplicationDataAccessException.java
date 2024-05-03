package com.vgt.ip.querier.exception;

public class IPApplicationDataAccessException extends RuntimeException {

    public IPApplicationDataAccessException(String message) {
        super(message);
    }

    public IPApplicationDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}