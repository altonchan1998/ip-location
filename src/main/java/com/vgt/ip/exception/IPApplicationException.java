package com.vgt.ip.exception;

public class IPApplicationException extends RuntimeException {

    public IPApplicationException(String message) {
        super(message);
    }

    public IPApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
