package com.vgt.ip.domain.applicationservice.handler;


public interface Handler<T, R> {
    R handle(T t);
}
