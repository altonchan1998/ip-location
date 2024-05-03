package com.vgt.ip.querier.applicationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Result<T> {
    private int code;
    private T data;
    private String msg;
}