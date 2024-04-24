package com.vgt.ip.domain.applicationservice.handler;


import reactor.core.publisher.Mono;

public interface Handler<T, R> {
    Mono<R> handle(T t);
}
