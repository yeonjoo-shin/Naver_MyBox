package com.numble.domain.response;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final StatusCode statusCode;

    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
