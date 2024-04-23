package com.vgt.ip.domain.core.exception;

public class IPApplicationException extends RuntimeException {

    public IPApplicationException(String message) {
        super(message);
    }

    public IPApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
