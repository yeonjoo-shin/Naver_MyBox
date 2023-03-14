package com.numble.response;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private T data;
    private int status;
    private String message;
    private String code;

    public CommonResponse(T data, StatusCode code) {
        this.data = data;
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.code = code.getCode();
    }

    public CommonResponse(StatusCode code) {
        this.data = null;
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.code = code.getCode();
    }

}
